package com.utp.adoptappbackend.user.service;

import com.utp.adoptappbackend.common.model.PageResponse;
import com.utp.adoptappbackend.common.model.enumeration.Role;
import com.utp.adoptappbackend.user.model.dto.AuthRegisterRequest;
import com.utp.adoptappbackend.user.model.dto.LoginResponse;
import com.utp.adoptappbackend.user.model.dto.UserRequest;
import com.utp.adoptappbackend.user.model.dto.UserResponse;
import com.utp.adoptappbackend.user.model.dto.UserUpdateRequest;

/**
 * Interfaz de servicio que define las operaciones de negocio para la gestión de usuarios.
 */
public interface UserService {
    
    /** Registra un nuevo usuario en el sistema. */
    UserResponse register(UserRequest request);
    
    /** Autentica a un usuario y genera un token de acceso. */
    LoginResponse login(AuthRegisterRequest request);
    
    /** Busca un usuario por su identificador único. */
    UserResponse findById(Long id);
    
    /** Actualiza la información de un usuario existente. */
    UserResponse update(Long id, UserUpdateRequest request);
    
    /** Obtiene una lista paginada de todos los usuarios. */
    PageResponse<UserResponse> findAll(int page, int size);
    
    /** Obtiene una lista paginada de usuarios filtrados por rol. */
    PageResponse<UserResponse> findByRole(Role role, int page, int size);
    
    /** Desactiva la cuenta de un usuario por su ID. */
    UserResponse deactivateUser(Long id);
    
    /** Activa la cuenta de un usuario por su ID. */
    UserResponse activateUser(Long id);
}