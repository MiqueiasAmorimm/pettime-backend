package com.pettime.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * 🧪 Unit tests for {@link ResourceNotFoundException}.
 * (FR) Tests unitaires pour {@link ResourceNotFoundException}.
 * Validates correct initialization of message, identifier,
 * and safe handling of null parameters.
 */
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Should create exception with message only and null identifier")
    void shouldCreateWithMessageOnly() {
        // Arrange
        String message = "Resource not found: User ID 99";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        // Assert
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(message);

        assertThat(exception.getIdentifier()).isNull();
    }

    @Test
    @DisplayName("Should create exception with resource and identifier and generate composite message")
    void shouldCreateWithMessageAndIdentifier() {
        // Arrange
        String resource = "User";
        Long id = 42L;

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(resource, id);

        // Assert
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(resource + " (identifier: " + id + ")");

        assertThat(exception.getIdentifier())
                .as("identifier should be stored correctly")
                .isEqualTo(id);
    }

    @Test
    @DisplayName("Should safely handle null message and null identifier")
    void shouldHandleNullValuesSafely() {
        // Act & Assert
        assertThatCode(() -> new ResourceNotFoundException(null, null))
                .doesNotThrowAnyException();

        ResourceNotFoundException ex = new ResourceNotFoundException(null, null);
        assertThat(ex.getMessage()).contains("identifier");
        assertThat(ex.getIdentifier()).isNull();
    }

    @Test
    @DisplayName("Should be a subtype of BusinessException for polymorphic handling")
    void shouldExtendBusinessException() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("User");

        // Assert
        assertThat(exception).isInstanceOf(BusinessException.class);
    }
}
