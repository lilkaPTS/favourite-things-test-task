package com.liluka.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    public void sentEmail(String email, String message) {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost("smtp.mail.yahoo.com");
        emailSender.setPort(465);

        emailSender.setUsername("lilspringboot@yahoo.com");
        emailSender.setPassword("");

        Properties props = emailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("lilspringboot@yahoo.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Confirmation code");
        simpleMailMessage.setText(message);

        emailSender.send(simpleMailMessage);
    }

}
