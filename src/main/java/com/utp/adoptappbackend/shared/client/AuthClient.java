package com.utp.adoptappbackend.shared.client;

import com.utp.adoptappbackend.common.model.ApiResponse;
import com.utp.adoptappbackend.common.util.WebClientUtils;

import com.utp.adoptappbackend.user.model.dto.AuthRegisterRequest;
import com.utp.adoptappbackend.user.model.dto.TokenClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthClient {
    private final WebClient authWebClient;
    private final WebClientUtils webClientUtils;

    public void register(AuthRegisterRequest request) {
        authWebClient.post()
                .uri("/auth/register")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, webClientUtils::handleError)
                .bodyToMono(Void.class)
                .block();
    }

    public TokenClientResponse login(AuthRegisterRequest request) {
        ApiResponse<TokenClientResponse> response = authWebClient.post()
                .uri("/auth/login")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, webClientUtils::handleError)
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<TokenClientResponse>>() {})
                .block();

        if (response != null && response.getData() != null) {
            return response.getData();
        }
        throw new IllegalStateException("Error al iniciar sesión en el servicio de autenticación");
    }
}