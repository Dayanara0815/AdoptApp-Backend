package com.utp.adoptappbackend.user.model.dto;

import com.utp.adoptappbackend.common.model.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta detallada de la información de un usuario.
 * Retorna los datos del perfil de usuario, su rol y la información de albergue si corresponde.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String dni;
    private String phone;
    private String address;
    private String avatar;
    private Role role;

    /**
     * Datos del albergue asociado. Este campo solo contiene información si el
     * usuario tiene asignado el rol de {@code Role.HOSTEL}.
     */
    private HostelResponse hostel;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}