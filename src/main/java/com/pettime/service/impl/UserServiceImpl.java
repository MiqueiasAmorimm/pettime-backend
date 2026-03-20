package com.pettime.service.impl;

import com.pettime.dto.UserRequestDto;
import com.pettime.dto.UserResponseDto;
import com.pettime.exception.EmailAlreadyExistsException;
import com.pettime.exception.InvalidUserDataException;
import com.pettime.exception.ResourceNotFoundException;
import com.pettime.mapper.UserMapper;
import com.pettime.model.User;
import com.pettime.repository.UserRepository;
import com.pettime.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User service implementation with secure password handling,
 * strong validation, DTO safety, logging and correct exception usage.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private UserResponseDto toSafeDto(User user) {
        return UserMapper.toResponseDto(user);
    }

    private void validateCreate(UserRequestDto dto) {

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new InvalidUserDataException("Email cannot be empty");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new InvalidUserDataException("Name cannot be empty");
        }
    }

    private void validateUpdate(Long id, UserRequestDto dto) {

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new InvalidUserDataException("Email cannot be empty");
        }

        userRepository.findByEmail(dto.getEmail())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new EmailAlreadyExistsException(dto.getEmail());
                    }
                });

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new InvalidUserDataException("Name cannot be empty");
        }
    }

    @Override
    public List<UserResponseDto> findAll() {
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(this::toSafeDto)
                .toList();
    }

    @Override
    public Optional<UserResponseDto> findById(Long id) {
        log.info("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .map(this::toSafeDto);
    }

    @Override
    public Optional<UserResponseDto> findByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .map(this::toSafeDto);
    }

    @Transactional
    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        log.info("Creating new user: {}", dto.getEmail());

        validateCreate(dto);

        User entity = UserMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(entity);

        log.info("User created successfully with ID: {}", saved.getId());
        return toSafeDto(saved);
    }

    @Transactional
    @Override
    public Optional<UserResponseDto> updateUser(Long id, UserRequestDto dto) {
        log.info("Updating user ID: {}", id);

        validateUpdate(id, dto);

        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());

                    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                        existing.setPassword(passwordEncoder.encode(dto.getPassword()));
                    }

                    User updated = userRepository.save(existing);
                    log.info("User updated successfully: {}", updated.getId());

                    return toSafeDto(updated);
                });
    }

    @Transactional
    @Override
    public boolean deleteUser(Long id) {
        log.warn("Deleting user ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted successfully: {}", id);

        return true;
    }
}