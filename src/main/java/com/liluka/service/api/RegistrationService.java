package com.liluka.service.api;

import com.liluka.dto.RegistrationUserDTO;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {
    ResponseEntity<String> createUser(RegistrationUserDTO userDTO);
    ResponseEntity<String> sendConfirmationCode(String email);
    ResponseEntity<String> activateUser(String token);
}
