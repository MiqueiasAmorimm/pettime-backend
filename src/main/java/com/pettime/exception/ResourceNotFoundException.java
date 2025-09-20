package com.pettime.exception;

/**
 * Exception thrown when a resource is not found.
 * Exception lancée lorsqu'une ressource n'est pas trouvée.
 */

public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor with resource name and identifier
     * @param resourceName Name of the resouce / Nom de la ressource
     * @param identifier identifier of the resource / Identifiant de la resource
     */
    public ResourceNotFoundException(String resourceName, String identifier) {
        super(String.format("%s not found with identifier: %s", resourceName, identifier));
    }
}
