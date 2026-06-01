package com.utp.adoptappbackend.user.model.dto;

import com.utp.adoptappbackend.common.model.enumeration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la solicitud para registrar un nuevo usuario en el
 * sistema.
 * Agrupa los datos personales obligatorios, de contacto, las credenciales de
 * acceso,
 * el rol asignado y, en caso de ser un albergue, la información adicional
 * asociada.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "El nombre completo es obligatorio")
    private String fullName;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    private String phone;
    private String address;

    @NotNull(message = "El rol es obligatorio")
    private Role role;

    /**
     * Información específica del albergue. Este campo es requerido y procesado
     * únicamente si el rol seleccionado es {@code Role.HOSTEL}.
     */
    private HostelRequest hostel;
}