package com.liluka.service;

import com.liluka.enums.Role;
import com.liluka.enums.Status;
import com.liluka.model.dao.User;
import com.liluka.model.dto.RegistrationUserDTO;
import com.liluka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    public boolean createUser(RegistrationUserDTO userDTO) {
        try {
            User user = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getName(), Role.USER, Status.CONFIRMATION);
            userRepository.save(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
