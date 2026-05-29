package com.utp.adoptappbackend.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRegisterRequest {

    @NotBlank(message = "El correo es obligatorio.")
    private String email;
    @NotBlank(message = "La contraseña es requerida.")
    private String password;
    private String role;
    private UUID externalId;
}
