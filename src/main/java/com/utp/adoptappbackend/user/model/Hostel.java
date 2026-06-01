package com.utp.adoptappbackend.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa la información de negocio de un albergue.
 * Comparte su clave primaria en una relación uno a uno (Shared Primary Key) con la entidad {@link User}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hostels")
public class Hostel {

    @Id
    private Long id; // Este ID será idéntico al ID de User

    /**
     * Relación uno a uno con la cuenta de usuario del albergue.
     * Utiliza {@code @MapsId} para que el ID del albergue sea el mismo que el de su usuario correspondiente.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "hostel_name", columnDefinition = "TEXT")
    private String hostelName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    private Integer capacity;

    @Column(columnDefinition = "TEXT")
    private String logo;

    @Column(columnDefinition = "TEXT", name = "donation_link")
    private String donationLink;

    @Column(columnDefinition = "TEXT")
    private String website;

    @Column(columnDefinition = "TEXT", name = "facebook_url")
    private String facebookUrl;

    @Column(columnDefinition = "TEXT", name = "instagram_url")
    private String instagramUrl;

    @Column(nullable = false, name = "is_verified")
    private Boolean isVerified = false;

    @Column(nullable = false, name = "is_active")
    private Boolean isActive = true;
}