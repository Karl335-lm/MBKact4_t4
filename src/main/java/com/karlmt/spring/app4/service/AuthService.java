package com.karlmt.spring.app4.service;

import com.karlmt.spring.app4.dto.AuthResponse;
import com.karlmt.spring.app4.dto.LoginRequest;
import com.karlmt.spring.app4.dto.RegisterRequest;
import com.karlmt.spring.app4.models.Usuario;
import com.karlmt.spring.app4.repository.UsuarioRepository;
import com.karlmt.spring.app4.security.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(RegisterRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo ya está registrado"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());

        // La contraseña se guarda cifrada con BCrypt
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        // El usuario no puede elegir su propio rol
        usuario.setRol("ROLE_USER");

        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(usuario.getEmail());

        return new AuthResponse(token, "Bearer");
    }

    public AuthResponse iniciarSesion(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException(
                        "Correo o contraseña incorrectos"
                ));

        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword())) {

            throw new BadCredentialsException(
                    "Correo o contraseña incorrectos"
            );
        }

        String token = jwtService.generarToken(usuario.getEmail());

        return new AuthResponse(token, "Bearer");
    }
}