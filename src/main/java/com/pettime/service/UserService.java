package com.pettime.service;

import com.pettime.dto.UserDto;
import com.pettime.model.User;
import com.pettime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // =========================
    // Get all users
    // =========================
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // =========================
    // Get user by ID
    // =========================
    public Optional<User> findByIdOptional(Long id) {
        return userRepository.findById(id.toString());
    }

    // =========================
    // Get user by email
    // =========================
    public Optional<User> findByEmailOptional(String email) {
        return userRepository.findByEmail(email);
    }

    // =========================
    // Create new user
    // =========================
    public User createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        User user = userDto.toEntity();
        return userRepository.save(user);
    }

    // =========================
    // Update existing user
    // =========================
    public Optional<User> updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id.toString()).map(user -> {
            user.setEmail(userDto.getEmail());
            user.setRole(userDto.getRole());
            return userRepository.save(user);
        });
    }

    // =========================
    // Delete user
    // =========================
    public boolean deleteUser(Long id) {
        return userRepository.findById(id.toString()).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    // =========================
    // Check if email exists
    // =========================
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
