package com.liluka.service.api;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

public interface LoggingService {
    void logInteraction(ServerHttpRequest request, ServerHttpResponse response);
}
