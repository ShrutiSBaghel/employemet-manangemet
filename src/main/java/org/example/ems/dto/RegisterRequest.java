package org.example.ems.dto;

public record RegisterRequest(
        String userName,
        String email,
        String password,
        String role
) {}
