package com.utp.adoptappbackend.user.service;

import com.utp.adoptappbackend.user.model.dto.AuthRegisterRequest;
import com.utp.adoptappbackend.user.model.dto.LoginResponse;
import com.utp.adoptappbackend.user.model.dto.UserRequest;
import com.utp.adoptappbackend.user.model.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRequest request);
    LoginResponse login(AuthRegisterRequest request);
    UserResponse findById(Long id);
}

