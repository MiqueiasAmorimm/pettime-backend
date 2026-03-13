package com.pettime.controller;

import com.pettime.dto.UserRequestDto;
import com.pettime.dto.UserResponseDto;
import com.pettime.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing users.
 * (FR) Contrôleur REST pour la gestion des utilisateurs.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(
        name = "Users",
        description = "Operations related to user management"
)
public class UserController {

    private final UserService userService;

    /**
     * Retrieve all users.
     */
    @Operation(
            summary = "List users",
            description = "Retrieves all registered users"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Retrieve user by ID.
     */
    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a user by its unique identifier"
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve user by email.
     */
    @Operation(
            summary = "Get user by email",
            description = "Retrieves a user by email address"
    )
    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create new user.
     */
    @Operation(
            summary = "Create user",
            description = "Creates a new user in the system"
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto created = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update existing user.
     */
    @Operation(
            summary = "Update user",
            description = "Updates an existing user by its identifier"
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto userRequestDto
    ) {
        return userService.updateUser(id, userRequestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete user.
     */
    @Operation(
            summary = "Delete user",
            description = "Deletes a user by its identifier"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}