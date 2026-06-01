package com.utp.adoptappbackend.user.mapper;

import com.utp.adoptappbackend.user.model.User;
import com.utp.adoptappbackend.user.model.dto.UserRequest;
import com.utp.adoptappbackend.user.model.dto.UserResponse;
import com.utp.adoptappbackend.user.model.Hostel;
import com.utp.adoptappbackend.user.model.dto.HostelResponse;
import com.utp.adoptappbackend.user.model.dto.UserUpdateRequest;
import com.utp.adoptappbackend.user.model.dto.HostelRequest;
import org.mapstruct.*;

/**
 * Mapeador de MapStruct para realizar conversiones entre entidades del dominio de usuarios y albergues
 * y sus correspondientes objetos de transferencia de datos (DTOs).
 * Configurado como componente de Spring para permitir inyección de dependencias.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    /**
     * Convierte un DTO de registro de usuario (UserRequest) en una entidad User.
     * Ignora campos generados automáticamente y establece el estado activo por defecto.
     *
     * @param request DTO con la información de registro del usuario.
     * @return Entidad User mapeada.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hostel", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", expression = "java(true)")
    User toEntity(UserRequest request);

    /**
     * Convierte una entidad User en un DTO de respuesta (UserResponse).
     *
     * @param entity Entidad User a convertir.
     * @return DTO UserResponse con la información para el cliente.
     */
    UserResponse toResponse(User entity);

    /**
     * Convierte una entidad Hostel (Albergue) en un DTO de respuesta (HostelResponse).
     *
     * @param entity Entidad Hostel a convertir.
     * @return DTO HostelResponse con la información detallada del albergue.
     */
    HostelResponse toHostelResponse(Hostel entity);

    /**
     * Convierte un DTO de solicitud de albergue (HostelRequest) en una entidad Hostel.
     * Ignora campos de identificación y relación, y establece el estado activo y no verificado por defecto.
     *
     * @param request DTO con la información del albergue.
     * @return Entidad Hostel mapeada.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isVerified", expression = "java(false)")
    @Mapping(target = "isActive", expression = "java(true)")
    Hostel toHostelEntity(HostelRequest request);

    /**
     * Actualiza el estado de una entidad User existente a partir de los datos provistos en un UserUpdateRequest.
     * Ignora campos clave y de auditoría que no deben ser modificados mediante esta acción.
     *
     * @param request DTO con los campos a actualizar.
     * @param entity  Entidad User de destino que será modificada.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "dni", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "hostel", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserFromDto(UserUpdateRequest request, @MappingTarget User entity);

    /**
     * Actualiza el estado de una entidad Hostel existente a partir de los datos de un HostelRequest.
     * Ignora campos clave, de auditoría y relaciones que no deben ser modificados.
     *
     * @param request DTO con la información de actualización del albergue.
     * @param entity  Entidad Hostel de destino que será modificada.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isVerified", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateHostelFromDto(HostelRequest request, @MappingTarget Hostel entity);
}