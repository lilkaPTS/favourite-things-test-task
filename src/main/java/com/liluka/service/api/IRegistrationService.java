package com.liluka.service.api;

import com.liluka.persistence.dto.RegistrationUserDTO;
import org.springframework.http.ResponseEntity;

public interface IRegistrationService {
    ResponseEntity<String> createUser(RegistrationUserDTO userDTO);
    ResponseEntity<String> sendConfirmationCode(String email);
    ResponseEntity<String> activateUser(String token);
}
