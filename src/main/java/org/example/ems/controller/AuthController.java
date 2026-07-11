package org.example.ems.controller;

import org.example.ems.dto.AuthResponse;
import org.example.ems.dto.LoginRequest;
import org.example.ems.dto.RegisterRequest;
import org.example.ems.model.User;
import org.example.ems.security.JwtService;
import org.example.ems.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UserService appUserService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        User user = appUserService.register(request);
        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = appUserService.findByName(request.userName())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}