package org.example.ems.dto;

public record UserDto(
        Long id,
        String userName,
        String role
) {}
