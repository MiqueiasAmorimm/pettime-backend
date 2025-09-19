package com.pettime.exception;

/**
 * Base class for all business-related exceptions.
 * Classe de base pour toutes les exceptions métier.
 */
public abstract class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
