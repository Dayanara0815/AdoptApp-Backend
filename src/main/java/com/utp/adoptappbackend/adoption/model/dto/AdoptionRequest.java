package com.utp.adoptappbackend.adoption.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionRequest {
    private Long petId;
    private Long adopterId;
}
