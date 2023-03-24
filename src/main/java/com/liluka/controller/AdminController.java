package com.liluka.controller;

import com.liluka.service.api.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasAnyAuthority('ADMIN_PERMISSION')")
    @GetMapping("/getAllUsernames")
    public ResponseEntity<List<String>> getAllUsernames() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllUsernames());
    }
}
