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
     *
     * @param pet   Pet entity to be created / Entité Pet à créer
     * @param owner Owner of the pet / Propriétaire de l'animal
     * @return Saved Pet entity / Entité Pet sauvegardée
     */
    public Pet createPet(Pet pet, User owner) {
        pet.setOwner(owner);
        log.info("Creating pet for owner: {}", owner.getEmail());
        return petRepository.save(pet);
    }

    /**
     * Retrieves all pets from the system.
     * Récupère tous les animaux du système.
     *
     * @return List of all pets / Liste de tous les animaux
     */
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    /**
     * Retrieves a pet by its unique ID.
     * Récupère un animal par son identifiant unique.
     *
     * @param id Pet ID / ID de l'animal
     * @return Pet entity / Entité Pet
     */
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with ID: " + id));
    }

    /**
     * Retrieves all pets belonging to a specific owner.
     * Récupère tous les animaux appartenant à un propriétaire spécifique.
     *
     * @param owner Owner / Propriétaire
     * @return List of pets for the owner / Liste d'animaux pour le propriétaire
     */
    public List<Pet> getPetsByOwner(User owner) {
        return petRepository.findByOwner(owner);
    }

    /**
     * Updates an existing pet using its ID and a Pet object with new data.
     * Met à jour un animal existant en utilisant son ID et un objet Pet avec de nouvelles données.
     *
     * @param id         ID of the pet to update / ID de l'animal à mettre à jour
     * @param updatedPet Pet object containing updated fields / Objet Pet contenant les champs mis à jour
     * @return Updated Pet entity / Entité Pet mise à jour
     */
    public Pet updatePet(Long id, Pet updatedPet) {
        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setName(updatedPet.getName());
                    existingPet.setBreed(updatedPet.getBreed());
                    existingPet.setAge(updatedPet.getAge());
                    existingPet.setOwner(updatedPet.getOwner());
                    log.info("Updated pet with ID: {}", id);
                    return petRepository.save(existingPet);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with ID: " + id));
    }

    /**
     * Updates an existing pet using the Pet object (must include ID).
     * Met à jour un animal existant en utilisant l'objet Pet (doit inclure l'ID).
     *
     * @param pet Pet entity with ID and updated data / Entité Pet avec ID et données mises à jour
     * @return Updated Pet entity / Entité Pet mise à jour
     */
    public Pet updatePet(Pet pet) {
        if (pet == null || pet.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet or Pet ID must not be null");
        }

        return petRepository.findById(pet.getId())
                .map(existingPet -> {
                    existingPet.setName(pet.getName());
                    existingPet.setBreed(pet.getBreed());
                    existingPet.setAge(pet.getAge());
                    existingPet.setOwner(pet.getOwner());
                    return petRepository.save(existingPet);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found with ID: " + pet.getId()));
    }

    /**
     * Deletes a pet by its ID.
     * Supprime un animal par son identifiant.
     *
     * @param id Pet ID / ID de l'animal
     */
    public void deletePet(Long id) {
        Pet pet = getPetById(id);
        petRepository.delete(pet);
        log.info("Deleted pet with ID: {}", id);
    }

    /**
     * Finds a pet by ID (alternative for internal use).
     * Trouve un animal par son identifiant (usage interne).
     *
     * @param id Pet ID / ID de l'animal
     * @return Pet entity / Entité Pet
     */
    public Pet findById(Long id) {
        return getPetById(id);
    }
}
