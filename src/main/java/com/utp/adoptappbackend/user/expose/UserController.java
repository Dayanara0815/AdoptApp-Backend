package com.utp.adoptappbackend.user.expose;

import com.utp.adoptappbackend.common.model.ApiResponse;
import com.utp.adoptappbackend.common.model.PageResponse;
import com.utp.adoptappbackend.common.model.enumeration.Role;
import com.utp.adoptappbackend.common.util.ConstantUtil;
import com.utp.adoptappbackend.user.model.dto.AuthRegisterRequest;
import com.utp.adoptappbackend.user.model.dto.LoginResponse;
import com.utp.adoptappbackend.user.model.dto.UserRequest;
import com.utp.adoptappbackend.user.model.dto.UserResponse;
import com.utp.adoptappbackend.user.model.dto.UserUpdateRequest;
import com.utp.adoptappbackend.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para registrarse, iniciar sesión, buscar, actualizar y
 * cambiar el estado de las cuentas de usuario.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Registra un nuevo usuario en el sistema.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(ConstantUtil.OK_CODE)
                .message(ConstantUtil.OK_MESSAGE)
                .data(userService.register(request))
                .build());
    }

    /**
     * Autentica a un usuario y genera un token de acceso.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody AuthRegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.<LoginResponse>builder()
                .code(ConstantUtil.OK_CODE)
                .message(ConstantUtil.OK_MESSAGE)
                .data(userService.login(request))
                .build());
    }

    /**
     * Busca y obtiene la información de un usuario por su identificador único.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(ConstantUtil.OK_CODE)
                .message(ConstantUtil.OK_MESSAGE)
                .data(userService.findById(id))
                .build());
    }

    /**
     * Actualiza la información de un usuario existente por su identificador.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(ConstantUtil.OK_CODE)
                .message(ConstantUtil.OK_MESSAGE)
                .data(userService.update(id, request))
                .build());
    }

    /**
     * Obtiene una lista paginada de todos los usuarios registrados.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.<PageResponse<UserResponse>>builder()
                .code(ConstantUtil.OK_CODE)
                .message(ConstantUtil.OK_MESSAGE)
                .data(userService.findAll(page, size))
                .build());
    }

    /**
     * Obtiene una lista paginada de usuarios filtrada por su rol.
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> findByRole(
            @PathVariable Role role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.<PageResponse<UserResponse>>builder()
                .code(ConstantUtil.OK_CODE)
                .message(ConstantUtil.OK_MESSAGE)
                .data(userService.findByRole(role, page, size))
                .build());
    }

    /**
     * Desactiva la cuenta de un usuario por su identificador.
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<UserResponse>> deactivateUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(ConstantUtil.OK_CODE)
                .message("Cuenta desactivada exitosamente.")
                .data(userService.deactivateUser(id))
                .build());
    }

    /**
     * Activa la cuenta de un usuario por su identificador.
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<UserResponse>> activateUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(ConstantUtil.OK_CODE)
                .message("Cuenta activada exitosamente.")
                .data(userService.activateUser(id))
                .build());
    }
}