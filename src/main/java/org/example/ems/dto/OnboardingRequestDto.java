package org.example.ems.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OnboardingRequestDto(
        Long requestId,
        String name,
        String email,
        String department,
        String designation,
        String reportingManager,
        LocalDate joiningDate,
        String requestedBy,
        LocalDateTime createdDate,
        String currentStatus,
        String currentApprover,
        String priority
) {}
