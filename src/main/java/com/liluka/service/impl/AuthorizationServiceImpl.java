package com.liluka.service.impl;

import com.liluka.config.jwt.JWTProvider;
import com.liluka.repository.UserRepository;
import com.liluka.dto.LoginDTO;
import com.liluka.model.User;
import com.liluka.service.api.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    public String login(LoginDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь %s не найден ", dto.getEmail())));

        return jwtProvider.createToken(dto.getEmail(), user.getRole().name());
    }
}
