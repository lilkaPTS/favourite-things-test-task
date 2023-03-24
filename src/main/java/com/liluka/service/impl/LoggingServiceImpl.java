package com.liluka.service.impl;

import com.liluka.config.jwt.JWTProvider;
import com.liluka.dto.LogEntryDTO;
import com.liluka.repository.LogEntryRepository;
import com.liluka.repository.UserRepository;
import com.liluka.model.LogEntry;
import com.liluka.model.User;
import com.liluka.service.api.LoggingService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoggingServiceImpl implements LoggingService {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final LogEntryRepository logEntryRepository;
    private final ModelMapper modelMapper;

    public void logInteraction(ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

        String token = request.getHeaders().getFirst("Authorization");

        User user;
        try {
            user = StringUtils.isNotBlank(token) ? userRepository.findByEmail(jwtProvider.getUsername(token)).orElse(null) : null;
        } catch (ExpiredJwtException ex) {
            log.info(ex.getMessage());
            user = null;
        }

        LogEntry logEntry = new LogEntry(user, request.getURI().toString(), HttpStatus.valueOf(servletResponse.getStatus()));

        logEntryRepository.save(logEntry);
        log.info(modelMapper.map(logEntry, LogEntryDTO.class));
    }
}
