package com.utp.adoptappbackend.user.repository;

import com.utp.adoptappbackend.common.model.enumeration.Role;
import com.utp.adoptappbackend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Administra las operaciones de persistencia de usuarios en la base de datos
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Busca un usuario por su dirección de correo electrónico única. */
    Optional<User> findByEmail(String email);

    /** Busca y pagina todos los usuarios que tengan un rol específico */
    Page<User> findByRole(Role role, Pageable pageable);
}