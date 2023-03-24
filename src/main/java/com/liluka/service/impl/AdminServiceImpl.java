package com.liluka.service.impl;

import com.liluka.repository.UserRepository;
import com.liluka.service.api.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public List<String> getAllUsernames() {
        return userRepository.findAllUserNames();
    }
}
