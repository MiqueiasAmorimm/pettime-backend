package com.pettime.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailAlreadyExistsExceptionTest {

    @Test
    @DisplayName("✔ Should store email and build correct error message")
    void shouldBuildCorrectMessage() {
        // Arrange
        String email = "duplicate@example.com";

        // Act
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(email);

        // Assert
        assertThat(exception).isInstanceOf(BusinessException.class);
        assertThat(exception.getMessage())
                .isEqualTo("Email already exists: " + email);
    }
}
