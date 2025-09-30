package com.pettime.service;

import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PetService
 * Tests unitaires pour PetService
 */
@ExtendWith(MockitoExtension.class)  // Modern JUnit 5 integration for Mockito
class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    private User owner;
    private Pet pet;

    @BeforeEach
    void setUp() {
        // Initialize test data
        // Initialiser les données de test
        owner = User.builder()
                .id("user1")
                .email("owner@example.com")
                .build();

        pet = Pet.builder()
                .id(1L)
                .name("Rex")
                .owner(owner)
                .build();
    }

    @Test
    void testCreatePet() {
        // Arrange: Mock repository save method
        // Préparer : simuler la méthode save du repository
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        // Act: Create pet
        // Exécuter : créer un animal
        Pet created = petService.createPet(pet, owner);

        // Assert: Verify creation
        // Vérifier : s'assurer de la création
        assertNotNull(created);
        assertEquals(owner, created.getOwner());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testGetAllPets() {
        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<Pet> pets = petService.getAllPets();

        assertEquals(1, pets.size());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void testGetPetById() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Optional<Pet> result = petService.getPetById(1L);

        assertTrue(result.isPresent());
        assertEquals("Rex", result.get().getName());
    }

    @Test
    void testGetPetsByOwner() {
        when(petRepository.findByOwner(owner)).thenReturn(List.of(pet));

        List<Pet> pets = petService.getPetsByOwner(owner);

        assertEquals(1, pets.size());
        assertEquals(owner, pets.get(0).getOwner());
    }

    @Test
    void testUpdatePet() {
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet updated = petService.updatePet(pet);

        assertEquals(pet.getName(), updated.getName());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testDeletePet() {
        doNothing().when(petRepository).deleteById(1L);

        petService.deletePet(1L);

        verify(petRepository, times(1)).deleteById(1L);
    }
}
