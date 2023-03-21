package com.liluka.controller;

import com.liluka.persistence.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${admin-controller.path}")
public class AdminController {

    private final UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    @GetMapping("/getUsers")
    public List<String> getUsers() {
        return userRepository.findAllUserNames();
    }
}
