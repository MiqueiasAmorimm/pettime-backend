package com.pettime.exception;

/**
 * Thrown when the petshop exists but is inactive.
 * Lancée lorsque le petshop existe mais est inactif.
 **/
public class PetshopInactiveException extends BusinessException {
    public PetshopInactiveException(String slug) {
        super("The petshop with link '" + slug + "' is temporarily inactive.");
    }
}
