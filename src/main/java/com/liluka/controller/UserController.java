package com.liluka.controller;

import com.liluka.dto.FavouriteThingsEntryDTO;
import com.liluka.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('USER_PERMISSION')")
    @PostMapping("/favourite")
    public ResponseEntity<String> favourite(@Valid @RequestBody @DateTimeFormat(pattern = "dd.MM.yyyy") FavouriteThingsEntryDTO dto, HttpServletRequest request) {
        userService.createFavouriteThingsEntry(dto, request);
        return ResponseEntity.status(HttpStatus.OK).body("Форма зарегистрирована");
    }
}
