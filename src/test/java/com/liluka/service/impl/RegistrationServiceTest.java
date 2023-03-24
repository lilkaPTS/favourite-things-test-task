package com.liluka.service.impl;


import com.liluka.enums.Role;
import com.liluka.exception.RegistrationException;
import com.liluka.repository.ConfirmationCodeRepository;
import com.liluka.repository.UserRepository;
import com.liluka.dto.RegistrationUserDTO;
import com.liluka.model.User;
import com.liluka.service.api.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    public RegistrationServiceTest() throws ParseException {
    }

    private final RegistrationUserDTO userDTO = new RegistrationUserDTO(
            "Гудима Илья Алексеевич",
            new SimpleDateFormat("yyyy-MM-dd").parse("2000-07-20"),
            "lilgud@mail.ru",
            "123321",
            "123321"
    );

    private final User user = new User("", "", "", new Date(), Role.USER);

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ConfirmationCodeRepository confirmationCodeRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    public void createUserIfUserNotFound() throws RegistrationException {
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        registrationService.createUser(userDTO);
    }

    @Test
    public void createUserIfUserFoundButNotEnabled() throws RegistrationException {
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        registrationService.createUser(userDTO);
    }

    @Test
    public void createUserIfUserFoundAndEnabled() {
        user.setEnabled(true);
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.createUser(userDTO));
    }

    @Test
    public void sendConfirmationCodeIfUserNotFound() {
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertFalse(registrationService.sendConfirmationCode(""));
    }

    @Test
    public void sendConfirmationCodeIfUserFoundButNotEnabled() {
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Assertions.assertTrue(registrationService.sendConfirmationCode(""));
    }

    @Test
    public void sendConfirmationCodeIfUserFoundAndEnabled() {
        user.setEnabled(true);
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Assertions.assertFalse(registrationService.sendConfirmationCode(""));
    }
}
