package com.pettime.service.impl;

import com.pettime.dto.UserDto;
import com.pettime.model.User;
import com.pettime.repository.UserRepository;
import com.pettime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserService.
 * (FR) Implémentation du service utilisateur.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Retrieve all users as DTOs.
     */
    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .toList();
    }

    /**
     * Retrieve user by ID.
     */
    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity);
    }

    /**
     * Retrieve user by email.
     */
    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDto::fromEntity);
    }

    /**
     * Create a new user.
     */
    @Override
    public UserDto createUser(UserDto dto) {
        User entity = dto.toEntity();
        User saved = userRepository.save(entity);
        return UserDto.fromEntity(saved);
    }

    /**
     * Update an existing user.
     */
    @Override
    public Optional<UserDto> updateUser(Long id, UserDto dto) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());

                    if (dto.getPassword() != null) {
                        existing.setPassword(dto.getPassword());
                    }

                    existing.setRole(dto.getRole());
                    return UserDto.fromEntity(userRepository.save(existing));
                });
    }

    /**
     * Delete user by ID.
     */
    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }
}
