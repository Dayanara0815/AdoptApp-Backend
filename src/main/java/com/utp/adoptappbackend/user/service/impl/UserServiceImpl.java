package com.utp.adoptappbackend.user.service.impl;

import com.utp.adoptappbackend.common.exception.ApiValidateException;
import com.utp.adoptappbackend.common.model.enumeration.Role;
import com.utp.adoptappbackend.common.util.ConstantUtil;
import com.utp.adoptappbackend.common.util.TokenUtil;
import com.utp.adoptappbackend.shared.client.AuthClient;
import com.utp.adoptappbackend.user.mapper.UserMapper;
import com.utp.adoptappbackend.user.model.Hostel;
import com.utp.adoptappbackend.user.model.PasswordResetToken;
import com.utp.adoptappbackend.user.model.User;
import com.utp.adoptappbackend.user.model.dto.AuthRegisterRequest;
import com.utp.adoptappbackend.user.model.dto.ForgotPasswordRequest;
import com.utp.adoptappbackend.user.model.dto.LoginResponse;
import com.utp.adoptappbackend.user.model.dto.ResetPasswordRequest;
import com.utp.adoptappbackend.user.model.dto.TokenClientResponse;
import com.utp.adoptappbackend.user.model.dto.UserRequest;
import com.utp.adoptappbackend.user.model.dto.UserResponse;
import com.utp.adoptappbackend.user.model.dto.UserUpdateRequest;
import com.utp.adoptappbackend.user.repository.PasswordResetTokenRepository;
import com.utp.adoptappbackend.user.repository.UserRepository;
import com.utp.adoptappbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthClient authClient;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    private static final long TOKEN_EXPIRY_MINUTES = 15;

    @Override
    @Transactional
    public UserResponse register(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiValidateException("El correo electrónico ya está registrado.");
        }

        // 1. Generar un externalId UUID aleatorio (requerido por ms-auth-service-helper)
        UUID externalId = UUID.randomUUID();

        // 2. Registrar credenciales en el microservicio de autenticación auxiliar
        AuthRegisterRequest authReq = AuthRegisterRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole().name())
                .externalId(externalId)
                .build();
        authClient.register(authReq);

        // 3. Guardar el usuario en la base de datos local de negocio
        User user = userMapper.toEntity(request);

        // Si es un Hostel, instanciamos la relación bidireccional
        if (request.getRole() == Role.HOSTEL && request.getHostel() != null) {
            Hostel hostel = Hostel.builder()
                    .user(user)
                    .hostelName(request.getHostel().getHostelName())
                    .description(request.getHostel().getDescription())
                    .capacity(request.getHostel().getCapacity())
                    .logo(request.getHostel().getLogo())
                    .donationLink(request.getHostel().getDonationLink())
                    .website(request.getHostel().getWebsite())
                    .facebookUrl(request.getHostel().getFacebookUrl())
                    .instagramUrl(request.getHostel().getInstagramUrl())
                    .isVerified(false)
                    .build();
            user.setHostel(hostel);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(AuthRegisterRequest request) {
        // 1. Validar inicio de sesión en el servicio de autenticación
        TokenClientResponse token = authClient.login(request);

        // 2. Obtener los detalles del perfil desde el repositorio local
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiValidateException("Usuario no encontrado en la base de datos de negocio."));

        // 3. Retornar el response unificado
        return LoginResponse.builder()
                .tokenType(token.getTokenType())
                .accessToken(token.getAccessToken())
                .expiresIn(token.getExpiresIn())
                .refreshToken(token.getRefreshToken())
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiValidateException(ConstantUtil.NOT_FOUND));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiValidateException("Usuario no encontrado para actualización."));

        // MapStruct actualiza de forma automática los campos principales no nulos de User
        userMapper.updateUserFromDto(request, user);

        // Si es un Hostel, delegamos a MapStruct la actualización de la relación anidada
        if (user.getRole() == Role.HOSTEL && request.getHostel() != null) {
            Hostel hostel = user.getHostel();
            if (hostel == null) {
                hostel = new Hostel();
                hostel.setUser(user);
                user.setHostel(hostel);
            }
            // MapStruct actualiza automáticamente todos los campos no nulos de Hostel
            userMapper.updateHostelFromDto(request.getHostel(), hostel);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiValidateException("El correo no está registrado en el sistema."));

        // Invalidad token anterior si existe
        passwordResetTokenRepository.findByUserIdAndUsedFalse(user.getId())
                .ifPresent(token -> token.setUsed(true));

        // Generar nuevo token
        String token = TokenUtil.generateToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(expiryDate)
                .used(false)
                .build();

        passwordResetTokenRepository.save(resetToken);

        log.info("Token de recuperación de contraseña generado para el usuario: {}", user.getEmail());
        // TODO: Integrar con servicio de email para enviar el token al correo
        // emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ApiValidateException("Las contraseñas no coinciden.");
        }

        if (request.getNewPassword().length() < 8) {
            throw new ApiValidateException("La contraseña debe tener al menos 8 caracteres.");
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new ApiValidateException("El token de recuperación es inválido."));

        if (resetToken.getUsed()) {
            throw new ApiValidateException("El token de recuperación ya ha sido utilizado.");
        }

        if (LocalDateTime.now().isAfter(resetToken.getExpiryDate())) {
            throw new ApiValidateException("El token de recuperación ha expirado.");
        }

        User user = resetToken.getUser();
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedPassword);

        // Actualizar contraseña en el microservicio de autenticación
        try {
            authClient.updatePassword(user.getEmail(), request.getNewPassword());
        } catch (Exception e) {
            log.error("Error actualizando contraseña en el servicio de autenticación: {}", e.getMessage());
            throw new ApiValidateException("Error al actualizar la contraseña en el servicio de autenticación.");
        }

        resetToken.setUsed(true);
        userRepository.save(user);
        passwordResetTokenRepository.save(resetToken);

        log.info("Contraseña actualizada exitosamente para el usuario: {}", user.getEmail());
    }
}