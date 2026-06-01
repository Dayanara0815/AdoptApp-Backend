package com.utp.adoptappbackend.adoption.repository;

import com.utp.adoptappbackend.adoption.model.Adoption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    
    boolean existsByPetId(Long petId);

    @Query(value = "SELECT a FROM Adoption a " +
            "JOIN FETCH a.pet p " +
            "JOIN FETCH p.user " +
            "LEFT JOIN FETCH a.adopter u " +
            "WHERE (:search IS NULL OR :search = '' OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')))",
            countQuery = "SELECT COUNT(a) FROM Adoption a " +
            "LEFT JOIN a.adopter u " +
            "WHERE (:search IS NULL OR :search = '' OR " +
            "LOWER(a.pet.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Adoption> findFiltered(@Param("search") String search, Pageable pageable);
}
