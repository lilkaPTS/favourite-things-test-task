package com.liluka.service.impl;

import com.liluka.enums.Role;
import com.liluka.exeption.RegistrationException;
import com.liluka.persistence.dao.ConfirmationCodeRepository;
import com.liluka.persistence.dto.ActivateUserDTO;
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

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService implements IRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ConfirmationCodeRepository confirmationCodeRepository;

    //TODO dry - переделать на try/catch. Пофиксить двойное добавление пользователя (исправить isNotActivatedEmail)
    public ResponseEntity<String> createUser(RegistrationUserDTO userDTO) {
        if (isNotActivatedEmail(userDTO.getEmail())) {
            User user = new User(userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getName(), userDTO.getDob(), Role.USER);
            sendCode(userDTO.getEmail());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно создан, код подтверждения отправлен на почту");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким e-mail уже существует");
        }
    }

    //TODO dry - переделать на try/catch
    public ResponseEntity<String> sendConfirmationCode(String email) {
        if (isNotActivatedEmail(email)) {
            sendCode(email);
            return ResponseEntity.status(HttpStatus.OK).body("Код подтверждения отправлен");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с таким e-mail уже существует");
        }
    }

    public ResponseEntity<String> activateUser(ActivateUserDTO dto) {
        try {
            checkConfirmationCode(dto);

            User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                    new RegistrationException(String.format("Пользователь %s не найден ", dto.getEmail()), HttpStatus.NOT_FOUND));
            user.setEnabled(true);
            userRepository.save(user);

            return ResponseEntity.ok("Пользователь активирован");
        } catch (RegistrationException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
        }
    }

    private boolean isNotActivatedEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return !user.get().isEnabled();
        } else {
            return true;
        }
    }

    private void sendCode(String email) {
        Thread thread = new Thread(() -> {
            ConfirmationCode code = new ConfirmationCode(email, generateConfirmationCode());

            emailService.sentEmail(code.getEmail(), code.getCode());
            if (confirmationCodeRepository.existByEmail(email)) {
                confirmationCodeRepository.updateCode(code.getEmail(), code.getCode());
            } else {
                confirmationCodeRepository.save(code);
            }
        });
        thread.start();
    }

    private void checkConfirmationCode(ActivateUserDTO dto) {
        RegistrationException exception = new RegistrationException("Неверный код подтверждения", HttpStatus.BAD_REQUEST);

        ConfirmationCode confirmationCode = confirmationCodeRepository.findConfirmationCodeByEmail(dto.getEmail()).orElseThrow(() -> exception);
        if (!Objects.equals(confirmationCode.getCode(), dto.getCode())) throw exception;
    }

    private String generateConfirmationCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int number = (int) (Math.random() * 10);
            code.append(number % 2 == 0 ? String.valueOf((char) ((Math.random() * 26) + 65)) : String.valueOf(number));
        }
        return code.toString();
    }
}
