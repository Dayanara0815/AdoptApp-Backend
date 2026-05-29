package com.utp.adoptappbackend.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostelResponse {
    private String hostelName;
    private String description;
    private String address;
    private Integer capacity;
    private String logo;
    private String donationLink;
    private String website;
    private String facebookUrl;
    private String instagramUrl;
    private Boolean isVerified;
    private Boolean isActive;
}