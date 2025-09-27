package com.pettime.service;

import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    // Create a new pet associated with an owner
    // Créer un nouvel animal associé à un propriétaire
    public Pet createPet(Pet pet, User owner) {
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    // Retrieve all pets in the system
    // Récupérer tous les animaux du système
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // Retrieve a pet by its ID
    // Récupérer un animal par son identifiant
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    // Retrieve all pets that belong to a specific owner
    // Récupérer tous les animaux appartenant à un propriétaire spécifique
    public List<Pet> getPetsByOwner(User owner) {
        return petRepository.findByOwner(owner);
    }

    // Update pet details
    // Mettre à jour les détails d'un animal
    public Pet updatePet(Pet pet) {
        return petRepository.save(pet);
    }

    // Delete a pet by its ID
    // Supprimer un animal par son identifiant
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
