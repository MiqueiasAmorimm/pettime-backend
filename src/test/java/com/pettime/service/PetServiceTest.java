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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PetService using Mockito and AssertJ.
 * (FR) Tests unitaires pour PetService avec Mockito et AssertJ.
 */
@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    private User owner;
    private Pet pet;

    @BeforeEach
    void setUp() {
        owner = User.builder()
                .id(1L)
                .email("owner@example.com")
                .build();

        pet = Pet.builder()
                .id(1L)
                .name("Rex")
                .owner(owner)
                .build();
    }

    @Test
    void shouldCreatePetWhenValid() {
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet created = petService.createPet(pet, owner);

        assertThat(created).isNotNull();
        assertThat(created.getOwner()).isEqualTo(owner);
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void shouldReturnAllPets() {
        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<Pet> pets = petService.getAllPets();

        assertThat(pets).hasSize(1).containsExactly(pet);
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnPetByIdWhenExists() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Pet found = petService.getPetById(1L);

        assertThat(found).isEqualTo(pet);
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenPetDoesNotExist() {
        when(petRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> petService.getPetById(999L)
        );

        assertThat(exception.getStatusCode().value()).isEqualTo(404);
        assertThat(exception.getReason()).contains("Pet not found with ID: 999");
    }

    @Test
    void shouldReturnPetsByOwner() {
        when(petRepository.findByOwner(owner)).thenReturn(List.of(pet));

        List<Pet> pets = petService.getPetsByOwner(owner);

        assertThat(pets).hasSize(1).allMatch(p -> p.getOwner().equals(owner));
        verify(petRepository, times(1)).findByOwner(owner);
    }

    @Test
    void shouldUpdatePetWhenExists() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet updated = petService.updatePet(1L, pet);

        assertThat(updated).isEqualTo(pet);
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingPet() {
        when(petRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> petService.updatePet(999L, pet)
        );

        assertThat(exception.getStatusCode().value()).isEqualTo(404);
        assertThat(exception.getReason()).contains("Pet not found with ID: 999");
    }

    @Test
    void shouldDeletePetByIdWhenExists() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        doNothing().when(petRepository).delete(pet);

        petService.deletePet(1L);

        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).delete(pet);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingPet() {
        when(petRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> petService.deletePet(999L)
        );

        assertThat(exception.getStatusCode().value()).isEqualTo(404);
        assertThat(exception.getReason()).contains("Pet not found with ID: 999");
    }
}
