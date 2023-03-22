package com.liluka.service.impl;

import com.liluka.config.jwt.JWTProvider;
import com.liluka.convertor.LogEntryConvertor;
import com.liluka.persistence.dao.LogEntryRepository;
import com.liluka.persistence.dao.UserRepository;
import com.liluka.persistence.model.LogEntry;
import com.liluka.persistence.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoggingService {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final LogEntryRepository logEntryRepository;
    private final LogEntryConvertor logEntryConvertor;

    public void logInteraction(ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

        String token = request.getHeaders().getFirst("Authorization");
        User user = StringUtils.isNotBlank(token) ? userRepository.findByEmail(jwtProvider.getUsername(token)).orElse(null) : null;

        LogEntry logEntry = new LogEntry(user, request.getURI().toString(), HttpStatus.valueOf(servletResponse.getStatus()));

        logEntryRepository.save(logEntry);
        log.info(logEntryConvertor.convertToDto(logEntry));
    }
}
