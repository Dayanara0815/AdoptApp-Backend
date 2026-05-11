package com.utp.adoptappbackend.user.model.dto;

import com.utp.adoptappbackend.common.model.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private HostelResponse hostel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

