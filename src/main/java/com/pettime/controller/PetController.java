package com.pettime.controller;

import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.service.PetService;
import com.pettime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * REST controller responsible for managing pet-related operations.
 * Contrôleur REST responsable de la gestion des opérations liées aux animaux.
 */
@RestController
@RequestMapping("/api/pets") // ✅ Always prefix API routes for clarity and versioning.
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final UserService userService;

    /**
     * Creates a new pet associated with an existing user.
     * Crée un nouvel animal associé à un utilisateur existant.
     */
    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<Pet> createPet(
            @PathVariable String ownerId,
            @RequestBody Pet pet
    ) {
        try {
            User owner = userService.findById(ownerId);
            Pet createdPet = petService.createPet(pet, owner);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating pet", e);
        }
    }

    /**
     * Retrieves all pets in the system.
     * Récupère tous les animaux dans le système.
     */
    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    /**
     * Retrieves all pets owned by a specific user.
     * Récupère tous les animaux appartenant à un utilisateur spécifique.
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Pet>> getPetsByOwner(@PathVariable String ownerId) {
        try {
            User owner = userService.findById(ownerId);
            List<Pet> pets = petService.getPetsByOwner(owner);
            return ResponseEntity.ok(pets);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");
        }
    }

    /**
     * Retrieves a pet by its ID.
     * Récupère un animal par son identifiant.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Pet pet = petService.findById(id);
        if (pet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        }
        return ResponseEntity.ok(pet);
    }

    /**
     * Updates pet information.
     * Met à jour les informations de l'animal.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(
            @PathVariable Long id,
            @RequestBody Pet updatedPet
    ) {
        try {
            Pet pet = petService.updatePet(id, updatedPet);
            return ResponseEntity.ok(pet);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        }
    }

    /**
     * Deletes a pet by its ID.
     * Supprime un animal par son identifiant.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        try {
            petService.deletePet(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        }
    }

}
