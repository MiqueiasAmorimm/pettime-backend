package com.pettime.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 🧪 Unit tests for {@link ApiError}.
 * (FR) Tests unitaires pour {@link ApiError}.
 */
class ApiErrorTest {

    @Test
    void shouldCreateApiErrorUsingAllArgsConstructor() {
        Instant now = Instant.now();
        ApiError error = new ApiError(now, 404, "Not Found", "Pet not found", "/api/pets/1");

        assertThat(error.getTimestamp()).isEqualTo(now);
        assertThat(error.getStatus()).isEqualTo(404);
        assertThat(error.getError()).isEqualTo("Not Found");
        assertThat(error.getMessage()).isEqualTo("Pet not found");
        assertThat(error.getPath()).isEqualTo("/api/pets/1");
    }

    @Test
    void shouldCreateApiErrorUsingHttpStatusConstructor() {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Invalid pet data", "/api/pets");

        assertThat(error.getStatus()).isEqualTo(400);
        assertThat(error.getError()).isEqualTo("Bad Request");
        assertThat(error.getMessage()).isEqualTo("Invalid pet data");
        assertThat(error.getPath()).isEqualTo("/api/pets");
        assertThat(error.getTimestamp()).isBeforeOrEqualTo(Instant.now());
    }

    @Test
    void shouldAllowUpdatingFieldsAfterCreation() {
        ApiError error = new ApiError();
        error.setStatus(500);
        error.setError("Internal Server Error");
        error.setMessage("Unexpected failure");
        error.setPath("/api/test");

        assertThat(error.getStatus()).isEqualTo(500);
        assertThat(error.getError()).isEqualTo("Internal Server Error");
        assertThat(error.getMessage()).isEqualTo("Unexpected failure");
        assertThat(error.getPath()).isEqualTo("/api/test");
    }

    @Test
    void shouldGenerateToStringAndEqualsConsistently() {
        ApiError error1 = new ApiError(HttpStatus.NOT_FOUND, "Pet not found", "/api/pets/1");
        ApiError error2 = new ApiError(HttpStatus.NOT_FOUND, "Pet not found", "/api/pets/1");

        assertThat(error1).isEqualTo(error2);
        assertThat(error1.toString()).contains("Pet not found");
    }
}
