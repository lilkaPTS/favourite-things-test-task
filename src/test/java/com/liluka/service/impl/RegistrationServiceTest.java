package com.liluka.service.impl;


import com.liluka.enums.Role;
import com.liluka.persistence.dao.ConfirmationCodeRepository;
import com.liluka.persistence.dao.UserRepository;
import com.liluka.persistence.model.ConfirmationCode;
import com.liluka.persistence.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private ConfirmationCodeRepository confirmationCodeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void sendConfirmationCodeIfUserNotFound() {
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertEquals(HttpStatus.OK, registrationService.sendConfirmationCode("").getStatusCode());
    }

    @Test
    public void sendConfirmationCodeIfUserFoundButNotEnabled() {
        User user = new User("", "", "", new Date(), Role.USER);
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Assertions.assertEquals(HttpStatus.OK, registrationService.sendConfirmationCode("").getStatusCode());
    }

    @Test
    public void sendConfirmationCodeIfUserFoundAndEnabled() {
        User user = new User("", "", "", new Date(), Role.USER);
        user.setEnabled(true);
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, registrationService.sendConfirmationCode("").getStatusCode());
    }
}
