package com.liluka.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PreAuthorize("hasAnyAuthority('USER_PERMISSION')")
    @GetMapping("/cars")
    public Set<String> cars() {
        return Set.of("bmw", "mercedes");
    }
}
