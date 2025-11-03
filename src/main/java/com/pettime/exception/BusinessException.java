package com.pettime.exception;

/**
 * Base class for all business-related exceptions.
 * Provides a unified structure for domain-specific runtime errors.
 */
public abstract class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
