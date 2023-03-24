package com.liluka.service.api;

import com.liluka.dto.RegistrationUserDTO;
import com.liluka.exception.RegistrationException;
import com.liluka.model.ConfirmationCode;
import com.liluka.model.User;

public interface RegistrationService {
    User createUser(RegistrationUserDTO userDTO) throws RegistrationException;

    boolean sendConfirmationCode(String email);

    ConfirmationCode activateUser(String token) throws RegistrationException;
}
