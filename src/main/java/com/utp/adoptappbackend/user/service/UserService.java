package com.utp.adoptappbackend.user.service;

import com.utp.adoptappbackend.common.model.PageResponse;
import com.utp.adoptappbackend.common.model.enumeration.Role;
import com.utp.adoptappbackend.user.model.dto.AuthRegisterRequest;
import com.utp.adoptappbackend.user.model.dto.LoginResponse;
import com.utp.adoptappbackend.user.model.dto.UserRequest;
import com.utp.adoptappbackend.user.model.dto.UserResponse;
import com.utp.adoptappbackend.user.model.dto.UserUpdateRequest;

/**
 * Interfaz de servicio que define las operaciones de negocio para la gestión de
 * usuarios.
 */
public interface UserService {

    /** Registra un nuevo usuario en el sistema. */
    UserResponse register(UserRequest request);

    LoginResponse login(AuthRegisterRequest request);

    UserResponse findById(Long id);

    UserResponse update(Long id, UserUpdateRequest request);

    PageResponse<UserResponse> findAll(int page, int size);

    PageResponse<UserResponse> findByRole(Role role, int page, int size);

    UserResponse deactivateUser(Long id);

    UserResponse activateUser(Long id);
    UserResponse findByEmail(String email);
}