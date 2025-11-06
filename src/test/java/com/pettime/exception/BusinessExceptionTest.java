package com.pettime.exception;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * 🧪 Unit tests for {@link BusinessException}.
 * Verifies core behavior, message propagation, and inheritance integrity.
 */
class BusinessExceptionTest {

    /**
     * A concrete subclass for testing purposes.
     * This allows us to instantiate and validate BusinessException behavior.
     */
    static class DummyBusinessException extends BusinessException {
        public DummyBusinessException(String message) {
            super(message);
        }

        public DummyBusinessException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Test
    void shouldRetainMessageInSingleArgConstructor() {
        String message = "Business rule violated";
        BusinessException ex = new DummyBusinessException(message);

        assertThat(ex)
                .isInstanceOf(RuntimeException.class)
                .hasMessage(message)
                .hasNoCause();
    }

    @Test
    void shouldRetainMessageAndCauseInDualArgConstructor() {
        Throwable cause = new IllegalArgumentException("Invalid parameter");
        String message = "Error processing entity";

        BusinessException ex = new DummyBusinessException(message, cause);

        assertThat(ex)
                .isInstanceOf(RuntimeException.class)
                .hasMessage(message)
                .hasCause(cause)
                .extracting(Throwable::getCause)
                .asInstanceOf(InstanceOfAssertFactories.THROWABLE)
                .hasMessage("Invalid parameter");
    }

    @Test
    void shouldBeAbstractAndExtendRuntimeException() {
        assertThat(BusinessException.class)
                .isAbstract()
                .isAssignableFrom(DummyBusinessException.class);

        assertThat(RuntimeException.class)
                .isAssignableFrom(BusinessException.class);
    }

    @Test
    void shouldPreserveStackTraceWhenChained() {
        Throwable cause = new NullPointerException("Missing data");
        BusinessException ex = new DummyBusinessException("Failed to execute business logic", cause);

        assertThat(ex.getStackTrace()).isNotEmpty();
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
