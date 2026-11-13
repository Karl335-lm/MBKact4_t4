package com.karlmt.spring.app4.controller;

import com.karlmt.spring.app4.dto.AuthResponse;
import com.karlmt.spring.app4.dto.LoginRequest;
import com.karlmt.spring.app4.dto.RegisterRequest;
import com.karlmt.spring.app4.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registro de usuarios
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrar(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse respuesta = authService.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // Inicio de sesión
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse respuesta = authService.iniciarSesion(request);

        return ResponseEntity.ok(respuesta);
    }
}