package org.example.ems.dto;

import java.time.LocalDate;

public record CreateOnboardingRequest(
        String name,
        String email,
        String department,
        String designation,
        String reportingManager,
        LocalDate joiningDate,
        String requestedBy,
        String currentApprover,
        String priority
) {}
