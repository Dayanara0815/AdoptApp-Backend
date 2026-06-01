package com.utp.adoptappbackend.adoption.model.dto;

import com.utp.adoptappbackend.pet.model.dto.PetResponse;
import com.utp.adoptappbackend.user.model.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionResponse {
    private Long id;
    private PetResponse pet;
    private UserResponse adopter;
    private LocalDateTime adoptionDate;
    private String status;
}
