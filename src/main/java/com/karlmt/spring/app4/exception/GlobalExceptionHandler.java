package com.karlmt.spring.app4.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Errores de validación de DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errores);
    }

    // Credenciales incorrectas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> manejarCredenciales(
            BadCredentialsException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("mensaje", "Correo o contraseña incorrectos");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // Errores HTTP controlados
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> manejarStatus(
            ResponseStatusException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("mensaje", ex.getReason());

        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }
}