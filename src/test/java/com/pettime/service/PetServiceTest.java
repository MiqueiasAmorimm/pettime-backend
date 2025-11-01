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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        // Arrange
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        // Act
        Pet created = petService.createPet(pet, owner);

        // Assert
        assertThat(created).isNotNull();
        assertThat(created.getOwner()).isEqualTo(owner);
        assertThat(created.getName()).isEqualTo("Rex");
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void shouldReturnAllPets() {
        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<Pet> pets = petService.getAllPets();

        assertThat(pets).hasSize(1)
                .first().isEqualTo(pet);
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnPetByIdWhenExists() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Optional<Pet> result = Optional.ofNullable(petService.getPetById(1L));

        assertThat(result).isPresent()
                .contains(pet);
    }

    @Test
    void shouldReturnEmptyWhenPetDoesNotExist() {
        when(petRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Pet> result = Optional.ofNullable(petService.getPetById(999L));

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnPetsByOwner() {
        when(petRepository.findByOwner(owner)).thenReturn(List.of(pet));

        List<Pet> pets = petService.getPetsByOwner(owner);

        assertThat(pets).hasSize(1)
                .allMatch(p -> p.getOwner().equals(owner));
    }

    @Test
    void shouldUpdatePet() {
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet updated = petService.updatePet(pet);

        assertThat(updated.getName()).isEqualTo(pet.getName());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void shouldDeletePetById() {
        doNothing().when(petRepository).deleteById(1L);

        petService.deletePet(1L);

        verify(petRepository, times(1)).deleteById(1L);
    }
}
