package com.file.backend.controller;

import com.file.backend.dto.LoginRequest;
import com.file.backend.dto.LoginResponse;
import com.file.backend.dto.RegisterRequest;
import com.file.backend.entity.User;
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

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        String result = authService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        if (result.equals("Registration successful")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(409).body(result);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        User user = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}