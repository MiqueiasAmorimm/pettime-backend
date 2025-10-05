package com.pettime.service;

import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public Pet createPet(Pet pet, User owner) {
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public List<Pet> getPetsByOwner(User owner) {
        return petRepository.findByOwner(owner);
    }

    public Pet updatePet(Pet pet) {
        return petRepository.save(pet);
    }

    public boolean deletePet(Long id) {
        return petRepository.findById(id).map(pet -> {
            petRepository.delete(pet);
            return true;
        }).orElse(false);
    }
}
