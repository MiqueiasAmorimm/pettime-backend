package com.pettime.exception;

/**
 * Thrown when a petshop with the given slug does not exist.
 * Lancée lorsqu'un petshop avec ce slug n'existe pas.
 */
public class PetshopNotFoundException extends BusinessException {
    public PetshopNotFoundException(String slug) {
        super("Petshop not found for link: " + slug);
    }
}
