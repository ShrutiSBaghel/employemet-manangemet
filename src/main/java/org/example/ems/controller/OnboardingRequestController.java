package org.example.ems.controller;

import org.example.ems.client.OnboardingRequestClient;
import org.example.ems.dto.ApprovedOnboardingResponse;
import org.example.ems.dto.CreateOnboardingRequest;
import org.example.ems.dto.EmployeeDto;
import org.example.ems.dto.OnboardingRequestDto;
import org.example.ems.dto.UpdateOnboardingRequest;
import org.example.ems.model.Employee;
import org.example.ems.service.EmployeeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/onboarding-requests")
public class OnboardingRequestController {
    private final OnboardingRequestClient onboardingRequestClient;
    private final EmployeeService employeeService;

    public OnboardingRequestController(
            OnboardingRequestClient onboardingRequestClient,
            EmployeeService employeeService
    ) {
        this.onboardingRequestClient = onboardingRequestClient;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<OnboardingRequestDto> getAllRequests() {
        return onboardingRequestClient.getAllRequests();
    }

    @GetMapping("/{requestId}")
    public OnboardingRequestDto getRequest(@PathVariable Long requestId) {
        return onboardingRequestClient.getRequest(requestId);
    }

    @PostMapping
    public OnboardingRequestDto createRequest(@RequestBody CreateOnboardingRequest request) {
        return onboardingRequestClient.createRequest(request);
    }

    @PutMapping("/{requestId}")
    public OnboardingRequestDto updateRequest(
            @PathVariable Long requestId,
            @RequestBody UpdateOnboardingRequest request
    ) {
        return onboardingRequestClient.updateRequest(requestId, request);
    }

    @PostMapping("/{requestId}/submit")
    public OnboardingRequestDto submitRequest(@PathVariable Long requestId) {
        return onboardingRequestClient.submitRequest(requestId);
    }

    @PostMapping("/{requestId}/approve")
    public Employee approveRequest(@PathVariable Long requestId) {
        ApprovedOnboardingResponse approvedRequest = onboardingRequestClient.approveRequest(requestId);
        EmployeeDto employee = new EmployeeDto();
        employee.setName(approvedRequest.name());
        employee.setDepartment(approvedRequest.department());
        return employeeService.createEmployee(employee);
    }

    @PostMapping("/{requestId}/reject")
    public OnboardingRequestDto rejectRequest(@PathVariable Long requestId) {
        return onboardingRequestClient.rejectRequest(requestId);
    }
}
