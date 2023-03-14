package com.liluka.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    @GetMapping("/getUsers")
    public List<String> getUsers() {
        return List.of("Вася", "Петя", "Ваня");
    }
}
