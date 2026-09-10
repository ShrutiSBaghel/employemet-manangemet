package org.example.ems.dto;

public record ApprovedOnboardingResponse(
        Long requestId,
        String name,
        String department
) {}
