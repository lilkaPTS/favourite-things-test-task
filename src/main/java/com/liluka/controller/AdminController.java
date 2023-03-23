package com.liluka.controller;

import com.liluka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    @GetMapping("/getUsers")
    public List<String> getUsers() {
        userRepository.findByEmail("lilgud@mail.ru").get().getLogEntries().forEach(System.out::println);
        return userRepository.findAllUserNames();
    }
}
