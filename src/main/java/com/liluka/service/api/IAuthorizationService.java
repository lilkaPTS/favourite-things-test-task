package com.liluka.service.api;

import com.liluka.persistence.dto.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthorizationService {
    ResponseEntity<?> login(LoginDTO dto);
}
