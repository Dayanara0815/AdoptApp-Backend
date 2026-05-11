package com.utp.adoptappbackend.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenClientResponse {
    private String tokenType;
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
}
