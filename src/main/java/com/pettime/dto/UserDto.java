package com.pettime.dto;

import com.pettime.model.User;
import com.pettime.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for User entity.
 * (FR) Objet de transfert de données pour l'entité Utilisateur.
 * <p>
 * Provides bidirectional conversion between User and UserDto,
 * ensuring data integrity and clear separation between the API layer and the domain model.
 * (FR) Fournit une conversion bidirectionnelle entre User et UserDto,
 * garantissant l’intégrité des données et une séparation claire entre la couche API et le modèle de domaine.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    /**
     * User's full name.
     * (FR) Nom complet de l'utilisateur.
     */
    @NotBlank(message = "Name is required")
    private String name;

    /**
     * User's email address.
     * (FR) Adresse e-mail de l'utilisateur.
     */
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * User's password.
     * (FR) Mot de passe de l'utilisateur.
     */
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * User's role in the system.
     * (FR) Rôle de l'utilisateur dans le système.
     */
    private UserRole role;

    /**
     * Convert this DTO to the User entity.
     * (FR) Convertir ce DTO en entité User.
     *
     * @return a User entity mapped from this DTO.
     */
    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }

    /**
     * Create a UserDto from a User entity.
     * (FR) Crée un UserDto à partir d'une entité User.
     *
     * @param user User entity
     * @return UserDto instance
     */
    public static UserDto fromEntity(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(null) // 🚨 Never expose passwords in responses
                .role(user.getRole())
                .build();
    }
}
