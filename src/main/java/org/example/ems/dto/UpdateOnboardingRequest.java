package org.example.ems.dto;

import java.time.LocalDate;

public record UpdateOnboardingRequest(
        String name,
        String email,
        String department,
        String designation,
        String reportingManager,
        LocalDate joiningDate,
        String requestedBy,
        String currentStatus,
        String currentApprover,
        String priority
) {}
