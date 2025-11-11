package com.pettime.service;

import com.pettime.dto.UserDto;
import com.pettime.model.User;
import com.pettime.model.UserRole;
import com.pettime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing user-related operations.
 * (FR) Couche de service pour la gestion des opérations liées aux utilisateurs.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Retrieve all users from the database.
     * (FR) Récupère tous les utilisateurs depuis la base de données.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Retrieve a user by their unique ID.
     * (FR) Récupère un utilisateur par son identifiant unique.
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(String.valueOf(id));
    }

    /**
     * Retrieve a user by their email address.
     * (FR) Récupère un utilisateur par son adresse e-mail.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Create a new user from a UserDto object.
     * (FR) Crée un nouvel utilisateur à partir d’un objet UserDto.
     */
    @Override
    public User createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already registered / (FR) E-mail déjà enregistré.");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setRole(UserRole.valueOf(String.valueOf(userDto.getRole())));

        return userRepository.save(user);
    }

    /**
     * Update an existing user by ID.
     * (FR) Met à jour un utilisateur existant via son identifiant.
     */
    @Override
    public Optional<User> updateUser(Long id, UserDto userDto) {
        return userRepository.findById(String.valueOf(id)).map(existingUser -> {
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPassword(userDto.getPassword());
            existingUser.setName(userDto.getName());
            existingUser.setRole(UserRole.valueOf(String.valueOf(userDto.getRole())));
            return userRepository.save(existingUser);
        });
    }

    /**
     * Delete a user by their ID.
     * (FR) Supprime un utilisateur par son identifiant.
     */
    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(String.valueOf(id))) return false;
        userRepository.deleteById(String.valueOf(id));
        return true;
    }
}
