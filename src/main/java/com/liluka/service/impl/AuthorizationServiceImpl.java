package com.liluka.service.impl;

import com.liluka.config.jwt.JWTProvider;
import com.liluka.repository.UserRepository;
import com.liluka.dto.LoginDTO;
import com.liluka.model.User;
import com.liluka.service.api.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    public ResponseEntity<?> login(LoginDTO dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
            User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь %s не найден ", dto.getEmail())));
            String token = jwtProvider.createToken(dto.getEmail(), user.getRole().name());

            Map<Object, Object> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("token", token);
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Неправильный логин или пароль!", HttpStatus.FORBIDDEN);
        }
    }
}
