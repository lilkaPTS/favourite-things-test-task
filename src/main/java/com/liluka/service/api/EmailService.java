package com.liluka.service.api;

public interface EmailService {
    void send(String email, String message, String subject);
}
