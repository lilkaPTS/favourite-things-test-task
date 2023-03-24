package com.liluka.controller.registration;

import com.liluka.dto.LoginDTO;
import com.liluka.dto.RegistrationUserDTO;
import com.liluka.exception.RegistrationException;
import com.liluka.service.api.AuthorizationService;
import com.liluka.service.api.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
@Validated
public class PublicController {

    private final AuthorizationService authorizationService;
    private final RegistrationService registrationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorizationService.login(dto));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неправильный логин или пароль!");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationUserDTO dto) {
        try {
            registrationService.createUser(dto);
        } catch (RegistrationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно создан, код подтверждения отправлен на почту");
    }

    @GetMapping("/sendConfirmationCode")
    public ResponseEntity<String> sendConfirmationCode(@NotBlank(message = "E-mail не может быть пустым")
                                                       @Email(message = "Введенное значение не является email")
                                                       @RequestParam("email") String email) {
        boolean result = registrationService.sendConfirmationCode(email);
        return ResponseEntity.status(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(result ? "Код подтверждения отправлен" : "Пользователь с таким e-mail уже существует");
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(@NotBlank(message = "Код не может быть пустым") String token) {
        try {
            registrationService.activateUser(token);
        } catch (RegistrationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Пользователь активирован");
    }
}
