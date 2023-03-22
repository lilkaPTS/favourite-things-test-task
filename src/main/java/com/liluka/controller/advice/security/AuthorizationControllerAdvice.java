package com.liluka.controller.advice.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizationControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<String> handleAccessDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Недостаточно прав");
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public final ResponseEntity<String> handleAuthenticationCredentialsNotFoundException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Данное действие требует авторизации");
    }

}
