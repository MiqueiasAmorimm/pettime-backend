package com.pettime.exception;

/**
 * Exception thrown when a resource is not found.
 * (FR) Exception lancée lorsqu'une ressource n'est pas trouvée.
 */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resourceName, String identifier) {
        super(String.format("%s not found with identifier: %s", resourceName, identifier));
    }
}
