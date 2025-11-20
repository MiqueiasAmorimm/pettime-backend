package com.pettime.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidDto_WhenAllFieldsAreProvidedCorrectly() {
        // Arrange
        UserRequestDto dto = UserRequestDto.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("StrongPass123")
                .build();

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidation_WhenNameIsBlank() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("")
                .email("john@example.com")
                .password("12345")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")
                        && v.getMessage().equals("Name is required"));
    }

    @Test
    void shouldFailValidation_WhenEmailIsBlank() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("John")
                .email("")
                .password("12345")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().equals("Email is required"));
    }

    @Test
    void shouldFailValidation_WhenEmailIsInvalid() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("John")
                .email("invalid-email")
                .password("12345")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().equals("Invalid email format"));
    }

    @Test
    void shouldFailValidation_WhenPasswordIsBlank() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("John")
                .email("john@example.com")
                .password("")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")
                        && v.getMessage().equals("Password is required"));
    }
}
