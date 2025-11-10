package com.pettime.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * 🧪 Unit tests for {@link PetshopInactiveException}.
 * (FR) Tests unitaires pour {@link PetshopInactiveException}.
 * Validates message formatting, null safety, and inheritance
 * from {@link BusinessException}.
 */
class PetshopInactiveExceptionTest {

    @Test
    @DisplayName("Should create PetshopInactiveException with formatted message")
    void shouldCreateExceptionWithProperMessage() {
        // Arrange
        String slug = "happy-paws";

        // Act
        PetshopInactiveException exception = new PetshopInactiveException(slug);

        // Assert
        assertThat(exception)
                .isInstanceOf(PetshopInactiveException.class)
                .hasMessage("The petshop with link '" + slug + "' is temporarily inactive.");
    }

    @Test
    @DisplayName("Should handle null slug safely without throwing exceptions")
    void shouldHandleNullSlugSafely() {
        // Act & Assert
        assertThatCode(() -> new PetshopInactiveException(null))
                .doesNotThrowAnyException();

        PetshopInactiveException exception = new PetshopInactiveException(null);

        assertThat(exception.getMessage())
                .as("Message should remain well-formed even when slug is null")
                .isEqualTo("The petshop with link 'null' is temporarily inactive.");
    }

    @Test
    @DisplayName("Should extend BusinessException for consistent handling")
    void shouldExtendBusinessException() {
        // Arrange
        PetshopInactiveException exception = new PetshopInactiveException("cool-petshop");

        // Assert
        assertThat(exception).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("Should not modify message formatting across multiple instances")
    void shouldKeepMessageFormattingConsistent() {
        // Arrange
        PetshopInactiveException ex1 = new PetshopInactiveException("dog-lovers");
        PetshopInactiveException ex2 = new PetshopInactiveException("dog-lovers");

        // Assert
        assertThat(ex1.getMessage())
                .as("Each instance should produce the same formatted message")
                .isEqualTo(ex2.getMessage());
    }
}
