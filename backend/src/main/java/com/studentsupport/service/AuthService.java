package com.studentsupport.service;

import com.studentsupport.dto.auth.*;
import com.studentsupport.entity.User;
import com.studentsupport.entity.enums.Role;
import com.studentsupport.repository.UserRepository;
import com.studentsupport.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository users, PasswordEncoder encoder, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.users = users;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        if (users.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .fullName(req.getFullName().trim())
                .email(email)
                .passwordHash(encoder.encode(req.getPassword()))
                .role(Role.STUDENT)
                .createdAt(Instant.now())
                .build();

        User saved = users.save(user);

        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole().name());
        return new AuthResponse(token, saved.getEmail(), saved.getRole().name(), saved.getId());
    }

    public AuthResponse login(LoginRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, req.getPassword())
        );

        User user = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid login"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getEmail(), user.getRole().name(), user.getId());
    }
}
