package com.liluka.service.api;

import com.liluka.dto.LoginDTO;

public interface AuthorizationService {
    String login(LoginDTO dto);
}
