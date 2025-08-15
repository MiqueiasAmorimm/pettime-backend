package com.pettime.repository;

import com.pettime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email); // Busca por email
    boolean existsByEmail(String email); // Verifica se email já existe
}