package com.liluka.controller.registration;

import com.liluka.persistence.dto.LoginDTO;
import com.liluka.persistence.dto.RegistrationUserDTO;
import com.liluka.service.RegistrationService;
import com.liluka.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/public")
public class PublicController {

    private final AuthorizationService authorizationService;
    private final RegistrationService registrationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        return authorizationService.login(dto);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationUserDTO dto) {
        boolean createStatus = registrationService.createUser(dto);
        String message = String.format(createStatus ? "Пользователь %s - успешно создан" : "Не удалось создать пользователя", dto.getName());
        log.info(message);
        return ResponseEntity.status(createStatus ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
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
