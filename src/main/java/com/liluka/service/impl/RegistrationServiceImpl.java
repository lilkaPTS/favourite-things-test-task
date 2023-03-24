package com.liluka.service.impl;

import com.liluka.enums.Role;
import com.liluka.exception.RegistrationException;
import com.liluka.repository.ConfirmationCodeRepository;
import com.liluka.model.ConfirmationCode;
import com.liluka.model.User;
import com.liluka.dto.RegistrationUserDTO;
import com.liluka.repository.UserRepository;
import com.liluka.service.api.EmailService;
import com.liluka.service.api.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ConfirmationCodeRepository confirmationCodeRepository;

    public User createUser(RegistrationUserDTO userDTO) throws RegistrationException {
        Optional<User> foundUser = userRepository.findByEmail(userDTO.getEmail());
        if (foundUser.isPresent() && foundUser.get().isEnabled()) {
            throw new RegistrationException("Пользователь с таким e-mail уже существует");
        } else foundUser.ifPresent(user -> {
            confirmationCodeRepository.deleteByUser(user);
            userRepository.delete(user);
        });

        User user = new User(userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getName(), userDTO.getDob(), Role.USER);
        userRepository.save(user);
        sendCodeAsync(user);
        return user;
    }

    public boolean sendConfirmationCode(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isEmpty() || foundUser.get().isEnabled()) {
            return false;
        } else {
            sendCodeAsync(foundUser.get());
            return true;
        }
    }

    public ConfirmationCode activateUser(String token) throws RegistrationException {
        ConfirmationCode confirmationCode = confirmationCodeRepository.findByToken(token)
                .orElseThrow(() -> new RegistrationException("Неверный код подтверждения"));

        User user = confirmationCode.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return confirmationCode;
    }

    private void sendCodeAsync(User user) {
        String token = UUID.randomUUID().toString();

        ConfirmationCode confirmationCode = confirmationCodeRepository.findByUser(user).orElse(new ConfirmationCode(user, token));
        confirmationCode.setToken(token);
        confirmationCodeRepository.save(confirmationCode);

        String message = "http://localhost:8080/api/public/activate?token=" + token;

        emailService.send(user.getEmail(), message, "Confirmation code");
    }
}
