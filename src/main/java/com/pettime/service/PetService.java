package com.pettime.service;

import com.pettime.dto.PetRequestDto;
import com.pettime.dto.PetResponseDto;
import com.pettime.exception.InvalidUserDataException;
import com.pettime.exception.ResourceNotFoundException;
import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.repository.PetRepository;
import com.pettime.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing pets using DTO-based contracts.
 * Service responsable de la gestion des animaux avec des contrats basés sur DTO.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    private PetResponseDto toResponseDto(Pet pet) {
        return PetResponseDto.fromEntity(pet);
    }

    private void validateRequest(PetRequestDto dto) {
        if (dto == null) {
            throw new InvalidUserDataException("Pet request cannot be null");
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new InvalidUserDataException("Pet name cannot be empty");
        }

        if (dto.getSpecies() == null || dto.getSpecies().isBlank()) {
            throw new InvalidUserDataException("Pet species cannot be empty");
        }

        if (dto.getOwnerId() == null) {
            throw new InvalidUserDataException("Owner ID cannot be null");
        }

        if (dto.getAge() != null && dto.getAge() < 0) {
            throw new InvalidUserDataException("Pet age cannot be negative");
        }
    }

    private User loadOwner(Long ownerId) {
        return userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with ID: " + ownerId));
    }

    @Transactional
    public PetResponseDto createPet(PetRequestDto dto) {
        validateRequest(dto);

        User owner = loadOwner(dto.getOwnerId());

        log.info("Creating pet '{}' for owner ID: {}", dto.getName(), owner.getId());

        Pet pet = Pet.builder()
                .name(dto.getName().trim())
                .species(dto.getSpecies().trim())
                .breed(dto.getBreed() != null && !dto.getBreed().isBlank() ? dto.getBreed().trim() : null)
                .age(dto.getAge())
                .owner(owner)
                .build();

        Pet saved = petRepository.save(pet);
        log.info("Pet created successfully with ID: {}", saved.getId());

        return toResponseDto(saved);
    }

    public List<PetResponseDto> findAll() {
        log.info("Fetching all pets");
        return petRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public Optional<PetResponseDto> findById(Long id) {
        log.info("Fetching pet by ID: {}", id);
        return petRepository.findById(id)
                .map(this::toResponseDto);
    }

    public List<PetResponseDto> findByOwnerId(Long ownerId) {
        log.info("Fetching pets by owner ID: {}", ownerId);

        if (!userRepository.existsById(ownerId)) {
            throw new ResourceNotFoundException("Owner not found with ID: " + ownerId);
        }

        return petRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Transactional
    public Optional<PetResponseDto> updatePet(Long id, PetRequestDto dto) {
        validateRequest(dto);
        User owner = loadOwner(dto.getOwnerId());

        log.info("Updating pet with ID: {}", id);

        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setName(dto.getName().trim());
                    existingPet.setSpecies(dto.getSpecies().trim());
                    existingPet.setBreed(dto.getBreed() != null && !dto.getBreed().isBlank() ? dto.getBreed().trim() : null);
                    existingPet.setAge(dto.getAge());
                    existingPet.setOwner(owner);

                    Pet updated = petRepository.save(existingPet);
                    log.info("Pet updated successfully with ID: {}", updated.getId());

                    return toResponseDto(updated);
                });
    }

    @Transactional
    public boolean deletePet(Long id) {
        log.warn("Deleting pet with ID: {}", id);

        if (!petRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pet not found with ID: " + id);
        }

        petRepository.deleteById(id);
        log.info("Pet deleted successfully with ID: {}", id);

        return true;
    }
}