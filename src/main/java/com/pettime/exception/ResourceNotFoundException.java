package com.pettime.exception;

import lombok.Getter;

/**
 * Exception thrown when a resource is not found.
 * (FR) Exception lancée lorsqu'une ressource n'est pas trouvée.
 */
@Getter
public class ResourceNotFoundException extends BusinessException {

    private final Object identifier;

    /**
     * Constructor when only the resource name is known.
     *
     * @param resourceName The name or description of the missing resource.
     */
    public ResourceNotFoundException(String resourceName) {
        super(resourceName);
        this.identifier = null;
    }

    /**
     * Constructor when both resource name and identifier are known.
     *
     * @param resourceName The name of the missing resource.
     * @param identifier   The identifier (ID or key) of the missing resource.
     */
    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(resourceName + " (identifier: " + identifier + ")");
        this.identifier = identifier;
    }
}
