package com.utp.adoptappbackend.adoption.model;

import com.utp.adoptappbackend.pet.model.Pet;
import com.utp.adoptappbackend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "adoptions")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopter_id", nullable = true)
    private User adopter;

    @Column(name = "adoption_date", nullable = false)
    private LocalDateTime adoptionDate;

    @Column(nullable = false)
    private String status;

    @PrePersist
    protected void onCreate() {
        if (this.adoptionDate == null) {
            this.adoptionDate = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "Finalizado";
        }
    }
}
