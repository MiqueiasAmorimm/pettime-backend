package com.pettime.repository;

import com.pettime.model.User;
import com.pettime.model.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // ---------------------------------------------------------
    // Helper
    // ---------------------------------------------------------
    private User createUser(String email) {
        return User.builder()
                .email(email)
                .name("Test User")
                .password("123456")
                .role(UserRole.CLIENT)
                .build();
    }

    // ---------------------------------------------------------
    // Tests
    // ---------------------------------------------------------

    @Test
    @DisplayName("✅ Should save and retrieve user by ID")
    void shouldPersistAndRetrieveUser() {
        // Arrange
        User saved = userRepository.save(createUser("john@pettime.ca"));

        // Act
        Optional<User> found = userRepository.findById(saved.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("john@pettime.ca");
    }

    @Test
    @DisplayName("🔎 Should find user by email")
    void shouldFindByEmail() {
        // Arrange
        userRepository.save(createUser("lookup@pettime.ca"));

        // Act
        Optional<User> found = userRepository.findByEmail("lookup@pettime.ca");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("lookup@pettime.ca");
    }

    @Test
    @DisplayName("❌ Should return empty when no user matches email")
    void shouldReturnEmptyForUnknownEmail() {
        // Act
        Optional<User> result = userRepository.findByEmail("missing@pettime.ca");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("🛑 Should reject duplicate email (unique constraint)")
    void shouldRejectDuplicateEmail() {
        // Arrange
        userRepository.saveAndFlush(createUser("unique@pettime.ca"));

        User duplicate = createUser("unique@pettime.ca");

        // Act
        Throwable thrown = catchThrowable(() -> userRepository.saveAndFlush(duplicate));

        // Assert
        assertThat(thrown)
                .isInstanceOf(DataIntegrityViolationException.class); // stable, bank independent
    }

    @Test
    @DisplayName("📌 existsByEmail should return true when email exists")
    void shouldReturnTrueWhenEmailExists() {
        // Arrange
        userRepository.save(createUser("exists@pettime.ca"));

        // Act
        boolean exists = userRepository.existsByEmail("exists@pettime.ca");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("📌 existsByEmail should return false when email does not exist")
    void shouldReturnFalseWhenEmailDoesNotExist() {
        // Act
        boolean exists = userRepository.existsByEmail("absent@pettime.ca");

        // Assert
        assertThat(exists).isFalse();
    }
}
