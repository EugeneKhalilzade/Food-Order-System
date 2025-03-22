package org.example.foodordersystem.controller;

import org.example.foodordersystem.model.dto.AuthRequestDTO;
import org.example.foodordersystem.model.dto.AuthResponseDTO;
import org.example.foodordersystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user-register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.register(authRequestDTO));
    }

    @PostMapping("/user-login")
    public ResponseEntity<?> login(@RequestBody AuthResponseDTO authResponseDTO) {
        String token = authService.login(authResponseDTO);
        return ResponseEntity.ok(Map.of("token", token));
    }
}