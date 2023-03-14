package com.liluka.service;

import com.liluka.enums.Role;
import com.liluka.persistence.model.User;
import com.liluka.persistence.dto.RegistrationUserDTO;
import com.liluka.persistence.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(RegistrationUserDTO userDTO) {
        try {
            User user = new User(userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getName(), userDTO.getDob(), Role.USER);
            userRepository.save(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
