package com.liluka.controller.registration;

import com.liluka.dto.LoginDTO;
import com.liluka.dto.RegistrationUserDTO;
import com.liluka.service.api.AuthorizationService;
import com.liluka.service.api.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/public")
@Validated
public class PublicController {

    private final AuthorizationService authorizationService;
    private final RegistrationService registrationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        return authorizationService.login(dto);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationUserDTO dto) {
       return registrationService.createUser(dto);
    }

    @GetMapping("/sendConfirmationCode")
    public ResponseEntity<String> sendConfirmationCode(@NotBlank(message = "E-mail не может быть пустым")
                                                           @Email(message = "Введенное значение не является email")
                                                           @RequestParam("email") String email) {
        return registrationService.sendConfirmationCode(email);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(@NotBlank(message = "Код не может быть пустым") String token) {
        return registrationService.activateUser(token);
    }
}
