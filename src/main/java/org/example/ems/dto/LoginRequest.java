package org.example.ems.dto;

public record LoginRequest(
        String userName,
        String password
) {}
