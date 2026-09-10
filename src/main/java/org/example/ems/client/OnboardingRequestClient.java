package org.example.ems.client;

import org.example.ems.dto.ApprovedOnboardingResponse;
import org.example.ems.dto.CreateOnboardingRequest;
import org.example.ems.dto.OnboardingRequestDto;
import org.example.ems.dto.UpdateOnboardingRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OnboardingRequestClient {
    private final RestClient restClient;
    private final String internalApiKey;

    public OnboardingRequestClient(
            RestClient.Builder restClientBuilder,
            @Value("${or.service.base-url}") String orServiceBaseUrl,
            @Value("${or.service.internal-api-key}") String internalApiKey
    ) {
        this.restClient = restClientBuilder.baseUrl(orServiceBaseUrl).build();
        this.internalApiKey = internalApiKey;
    }

    public List<OnboardingRequestDto> getAllRequests() {
        return restClient.get()
                .uri("/onboarding-requests")
                .header("X-Internal-Service-Key", internalApiKey)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public OnboardingRequestDto getRequest(Long requestId) {
        return restClient.get()
                .uri("/onboarding-requests/{requestId}", requestId)
                .header("X-Internal-Service-Key", internalApiKey)
                .retrieve()
                .body(OnboardingRequestDto.class);
    }

    public OnboardingRequestDto createRequest(CreateOnboardingRequest request) {
        return restClient.post()
                .uri("/onboarding-requests")
                .header("X-Internal-Service-Key", internalApiKey)
                .body(request)
                .retrieve()
                .body(OnboardingRequestDto.class);
    }

    public OnboardingRequestDto updateRequest(Long requestId, UpdateOnboardingRequest request) {
        return restClient.put()
                .uri("/onboarding-requests/{requestId}", requestId)
                .header("X-Internal-Service-Key", internalApiKey)
                .body(request)
                .retrieve()
                .body(OnboardingRequestDto.class);
    }

    public OnboardingRequestDto submitRequest(Long requestId) {
        return restClient.post()
                .uri("/onboarding-requests/{requestId}/submit", requestId)
                .header("X-Internal-Service-Key", internalApiKey)
                .retrieve()
                .body(OnboardingRequestDto.class);
    }

    public ApprovedOnboardingResponse approveRequest(Long requestId) {
        return restClient.post()
                .uri("/onboarding-requests/{requestId}/approve", requestId)
                .header("X-Internal-Service-Key", internalApiKey)
                .retrieve()
                .body(ApprovedOnboardingResponse.class);
    }

    public OnboardingRequestDto rejectRequest(Long requestId) {
        return restClient.post()
                .uri("/onboarding-requests/{requestId}/reject", requestId)
                .header("X-Internal-Service-Key", internalApiKey)
                .retrieve()
                .body(OnboardingRequestDto.class);
    }
}
