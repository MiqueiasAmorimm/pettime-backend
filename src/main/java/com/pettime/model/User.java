package com.pettime.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

/**
 * Represents a system user (client, petshop, or admin).
 * (FR) Représente un utilisateur du système (client, animalerie ou administrateur).
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's full name.
     * (FR) Nom complet de l'utilisateur.
     */
    @Column(nullable = false)
    private String name;

    /**
     * User's email address (unique).
     * (FR) Adresse e-mail de l'utilisateur (unique).
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Encrypted password.
     * (FR) Mot de passe chiffré.
     */
    @Column(nullable = false)
    private String password;

    /**
     * User role (CLIENT, PETSHOP, ADMIN, etc.).
     * (FR) Rôle de l'utilisateur (CLIENT, ANIMALERIE, ADMIN, etc.).
     */
    @Enumerated(EnumType.STRING)  // Ensure the role is stored as a string in the database
    @Column(nullable = false)
    private UserRole role;

    /**
     * Timestamp for record creation.
     * (FR) Horodatage de la création de l’enregistrement.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp for last update.
     * (FR) Horodatage de la dernière mise à jour.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
