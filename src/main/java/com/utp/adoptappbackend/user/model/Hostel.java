package com.utp.adoptappbackend.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hostels")
public class Hostel {

    @Id
    private Long id; // Este ID será idéntico al ID de User

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "hostel_name")
    private String hostelName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String address;

    private Integer capacity;

    private String logo;

    @Column(name = "donation_link")
    private String donationLink;

    private String website;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(nullable = false, name = "is_verified")
    private Boolean isVerified = false;

    @Column(nullable = false, name = "is_active")
    private Boolean isActive = true;
}