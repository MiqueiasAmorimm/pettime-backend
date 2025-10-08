package com.pettime.service;

import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Service responsible for managing Pet entities.
 * Service responsable de la gestion des entités Pet.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PetService {

    private final PetRepository petRepository;

    /**
     * Creates a new pet associated with a specific owner.
     * Crée un nouvel animal associé à un propriétaire spécifique.
     */
    public Pet createPet(Pet pet, User owner) {
        pet.setOwner(owner);
        log.info("Creating pet for owner: {}", owner.getEmail());
        return petRepository.save(pet);
    }

    /**
     * Retrieves all pets from the system.
     * Récupère tous les animaux du système.
     */
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    /**
     * Retrieves a pet by its unique ID.
     * Récupère un animal par son identifiant unique.
     */
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with ID: " + id));
    }

    /**
     * Retrieves all pets belonging to a specific owner.
     * Récupère tous les animaux appartenant à un propriétaire spécifique.
     */
    public List<Pet> getPetsByOwner(User owner) {
        return petRepository.findByOwner(owner);
    }

    /**
     * Updates an existing pet with new data.
     * Met à jour un animal existant avec de nouvelles données.
     */
    public Pet updatePet(Long id, Pet updatedPet) {
        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setName(updatedPet.getName());
                    existingPet.setBreed(updatedPet.getSpecies());
                    existingPet.setAge(updatedPet.getAge());
                    existingPet.setBreed(updatedPet.getBreed());
                    existingPet.setOwner(updatedPet.getOwner());
                    log.info("Updated pet with ID: {}", id);
                    return petRepository.save(existingPet);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with ID: " + id));
    }

    /**
     * Deletes a pet by its ID.
     * Supprime un animal par son identifiant.
     */
    public void deletePet(Long id) {
        Pet pet = getPetById(id);
        petRepository.delete(pet);
        log.info("Deleted pet with ID: {}", id);
    }

    /**
     * Finds a pet by ID (alternative for internal use).
     * Trouve un animal par son identifiant (usage interne).
     */
    public Pet findById(Long id) {
        return getPetById(id);
    }
}
