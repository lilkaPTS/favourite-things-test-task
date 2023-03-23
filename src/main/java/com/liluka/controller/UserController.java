package com.liluka.controller;

import com.liluka.dto.FavouriteThingsEntryDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@Log4j2
@RequestMapping("/api/user")
public class UserController {

    @PreAuthorize("hasAnyAuthority('USER_PERMISSION')")
    @GetMapping("/cars")
    public Set<String> cars() {
        return Set.of("bmw", "mercedes");
    }

    @PreAuthorize("hasAnyAuthority('USER_PERMISSION')")
    @PostMapping("/favourite")
    public ResponseEntity<String> favourite(@Valid @RequestBody FavouriteThingsEntryDTO dto) {
        return ResponseEntity.ok(dto.toString());
    }
}
