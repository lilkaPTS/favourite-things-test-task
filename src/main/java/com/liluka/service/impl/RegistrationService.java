package com.liluka.service.impl;

import com.liluka.enums.Role;
import com.liluka.exeption.RegistrationException;
import com.liluka.persistence.dao.ConfirmationCodeRepository;
import com.liluka.persistence.model.ConfirmationCode;
import com.liluka.persistence.model.User;
import com.liluka.persistence.dto.RegistrationUserDTO;
import com.liluka.persistence.dao.UserRepository;
import com.liluka.service.api.IRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService implements IRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ConfirmationCodeRepository confirmationCodeRepository;

    public ResponseEntity<String> createUser(RegistrationUserDTO userDTO) {
        Optional<User> foundUser = userRepository.findByEmail(userDTO.getEmail());
        if (foundUser.isPresent() && foundUser.get().isEnabled()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким e-mail уже существует");
        } else foundUser.ifPresent(user -> {
            confirmationCodeRepository.deleteByUser(user);
            userRepository.delete(user);
        });

        User user = new User(userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getName(), userDTO.getDob(), Role.USER);
        userRepository.save(user);
        sendCodeAsync(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно создан, код подтверждения отправлен на почту");
    }

    public ResponseEntity<String> sendConfirmationCode(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isEmpty() || foundUser.get().isEnabled()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким e-mail уже существует");
        } else {
            sendCodeAsync(foundUser.get());
            return ResponseEntity.status(HttpStatus.OK).body("Код подтверждения отправлен");
        }
    }

    public ResponseEntity<String> activateUser(String token) {
        try {
            ConfirmationCode confirmationCode = confirmationCodeRepository.findByToken(token).orElseThrow(() ->
                    new RegistrationException("Неверный код подтверждения", HttpStatus.BAD_REQUEST));

            User user = confirmationCode.getUser();
            user.setEnabled(true);
            userRepository.save(user);

            return ResponseEntity.ok("Пользователь активирован");
        } catch (RegistrationException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
        }
    }

    private void sendCodeAsync(User user) {
        new Thread(() -> {
            String token = UUID.randomUUID().toString();

            ConfirmationCode confirmationCode = confirmationCodeRepository.findByUser(user).orElse(new ConfirmationCode(user, token));
            confirmationCode.setToken(token);
            confirmationCodeRepository.save(confirmationCode);

            String message = "http://localhost:8080/api/public/activate?token=" + token;
            emailService.sentEmail(user.getEmail(), message);
        }).start();
    }
}
