package com.utp.adoptappbackend.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostelRequest {

    @NotBlank(message = "El nombre del albergue es obligatorio")
    private String hostelName;

    private String description;
    private Integer capacity;
    private String logo;
    private String donationLink;
    private String website;
    private String facebookUrl;
    private String instagramUrl;
}
