package com.utp.adoptappbackend.pet.repository;

import com.utp.adoptappbackend.common.model.enumeration.Sex;
import com.utp.adoptappbackend.common.model.enumeration.Size;
import com.utp.adoptappbackend.common.model.enumeration.Species;
import com.utp.adoptappbackend.common.model.enumeration.Status;
import com.utp.adoptappbackend.pet.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByStatusNot(Status status);
    List<Pet> findByUserIdAndStatusNot(Long userId, Status status);

    @Query("SELECT DISTINCT p FROM Pet p " +
            "JOIN FETCH p.user " +  // ✅ AGREGAR ESTA LÍNEA
            "WHERE (:status IS NULL AND p.status <> com.utp.adoptappbackend.common.model.enumeration.Status.DELETED OR p.status = :status) " +
            "AND (:isSpeciesEmpty = true OR p.species IN :species) " +
            "AND (:size IS NULL OR p.size = :size) " +
            "AND (:sex IS NULL OR p.sex = :sex) " +
            "AND (:age IS NULL OR p.age = :age) " +
            "AND (:isSearchEmpty = true OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.user.fullName) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Pet> findFiltered(
            @Param("status") Status status,
            @Param("species") List<Species> species,
            @Param("isSpeciesEmpty") boolean isSpeciesEmpty,
            @Param("size") Size size,
            @Param("sex") Sex sex,
            @Param("age") String age,
            @Param("search") String search,
            @Param("isSearchEmpty") boolean isSearchEmpty,
            Pageable pageable);
}
