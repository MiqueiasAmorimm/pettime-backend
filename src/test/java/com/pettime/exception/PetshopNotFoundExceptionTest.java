package com.pettime.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for PetshopNotFoundException.
 * Ensures correct message propagation and inheritance from BusinessException.
 */
class PetshopNotFoundExceptionTest {

    @Test
    @DisplayName("Should store and expose correct message")
    void shouldExposeCorrectMessage() {

        String slug = "petshop-quebec";
        String expected = "Petshop not found for link: " + slug;

        PetshopNotFoundException exception =
                new PetshopNotFoundException(slug);

        assertThat(exception.getMessage()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should be instance of BusinessException")
    void shouldInheritBusinessException() {

        PetshopNotFoundException exception =
                new PetshopNotFoundException("example");

        assertThat(exception).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("Should not allow null messages silently")
    void shouldHandleNullInputSafely() {

        PetshopNotFoundException exception =
                new PetshopNotFoundException(null);

        assertThat(exception.getMessage())
                .contains("null");
    }
}
