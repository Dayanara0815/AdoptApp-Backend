package com.utp.adoptappbackend.user.service;

import com.utp.adoptappbackend.user.model.dto.AuthRegisterRequest;
import com.utp.adoptappbackend.user.model.dto.ForgotPasswordRequest;
import com.utp.adoptappbackend.user.model.dto.LoginResponse;
import com.utp.adoptappbackend.user.model.dto.ResetPasswordRequest;
import com.utp.adoptappbackend.user.model.dto.UserRequest;
import com.utp.adoptappbackend.user.model.dto.UserResponse;
import com.utp.adoptappbackend.user.model.dto.UserUpdateRequest;

public interface UserService {
    UserResponse register(UserRequest request);
    LoginResponse login(AuthRegisterRequest request);
    UserResponse findById(Long id);
    UserResponse update(Long id, UserUpdateRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}