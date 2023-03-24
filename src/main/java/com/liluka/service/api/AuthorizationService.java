package com.liluka.service.api;

import com.liluka.dto.LoginDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthorizationService {
    String login(LoginDTO dto);
}
