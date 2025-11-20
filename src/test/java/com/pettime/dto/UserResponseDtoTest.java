package com.pettime.dto;

import com.pettime.model.User;
import com.pettime.model.UserRole;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseDtoTest {

    @Test
    void fromEntity_ShouldMapAllRelevantFields_WhenValidUserProvided() {
        // Arrange
        User user = User.builder()
                .id(100L)
                .name("Alice")
                .email("alice@example.com")
                .password("EncryptedPass#123") // must not be mapped
                .role(UserRole.CLIENT)
                .build();

        // Act
        UserResponseDto dto = UserResponseDto.fromEntity(user);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(100L);
        assertThat(dto.getName()).isEqualTo("Alice");
        assertThat(dto.getEmail()).isEqualTo("alice@example.com");
        assertThat(dto.getRole()).isEqualTo(UserRole.CLIENT);
    }

    @Test
    void fromEntity_ShouldReturnNull_WhenInputIsNull() {
        // Arrange
        User user = null;

        // Act
        UserResponseDto dto = UserResponseDto.fromEntity(user);

        // Assert
        assertThat(dto).isNull();
    }

    @Test
    void fromEntity_ShouldNotExposePassword() {
        // Arrange
        User user = User.builder()
                .id(10L)
                .name("Bob")
                .email("bob@example.com")
                .password("SuperSecret") // must never appear in DTO
                .role(UserRole.ADMIN)
                .build();

        // Act
        UserResponseDto dto = UserResponseDto.fromEntity(user);

        // Assert
        assertThat(dto).isNotNull();
        // Confirm password was not included in DTO
        assertThat(dto.getClass().getDeclaredFields())
                .noneMatch(field -> field.getName().equals("password"));
    }
}
