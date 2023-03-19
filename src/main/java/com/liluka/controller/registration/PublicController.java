package com.liluka.controller.registration;

import com.liluka.persistence.dto.ActivateUserDTO;
import com.liluka.persistence.dto.LoginDTO;
import com.liluka.persistence.dto.RegistrationUserDTO;
import com.liluka.service.api.IAuthorizationService;
import com.liluka.service.api.IRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/public")
@Validated
public class PublicController {

    private final IAuthorizationService authorizationService;
    private final IRegistrationService registrationService;

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
    public ResponseEntity<String> activateUser(ActivateUserDTO dto) {
        return registrationService.activateUser(dto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.info(errors);
        return errors;
    }
}
