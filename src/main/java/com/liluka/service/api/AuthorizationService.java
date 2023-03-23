package com.liluka.service.api;

import com.liluka.dto.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface AuthorizationService {
    ResponseEntity<?> login(LoginDTO dto);
}
