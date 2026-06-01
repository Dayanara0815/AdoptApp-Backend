package com.utp.adoptappbackend.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO que representa la solicitud de autenticación y registro de credenciales
 * de usuario.
 * Se utiliza para intercambiar información con el microservicio de
 * autenticación auxiliar
 * al registrar nuevos usuarios o verificar credenciales en el inicio de sesión.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRegisterRequest {

    /** Correo electrónico único del usuario. Obligatorio para registro y login */
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    /**
     * Contraseña del usuario en texto plano para validación o encriptación.
     * Obligatoria
     */
    @NotBlank(message = "La contraseña es requerida")
    private String password;

    private String role;

    /**
     * Identificador único de usuario generado externamente para la sincronización
     * entre servicios
     */
    private UUID externalId;
}
