package com.file.backend.controller;

import com.file.backend.dto.LoginRequest;
import com.file.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request) {

        String result = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        if (result.equals("Login successful")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(401).body(result);
    }
}