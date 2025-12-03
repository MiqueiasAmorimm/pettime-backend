package com.pettime.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidUserDataExceptionTest {

    @Test
    @DisplayName("✔ Should correctly store custom message and extend BusinessException")
    void shouldCreateExceptionWithCorrectMessage() {
        // Arrange
        String message = "Invalid user data: email format is incorrect";

        // Act
        InvalidUserDataException exception = new InvalidUserDataException(message);

        // Assert
        assertThat(exception)
                .isInstanceOf(BusinessException.class);

        assertThat(exception.getMessage())
                .isEqualTo(message);
    }
}
