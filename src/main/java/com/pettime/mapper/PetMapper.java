package com.pettime.mapper;

import com.pettime.dto.PetRequestDto;
import com.pettime.dto.PetResponseDto;
import com.pettime.model.Pet;

public class PetMapper {

    public static Pet toEntity(PetRequestDto dto) {
        if (dto == null) return null;

        return Pet.builder()
                .name(dto.getName())
                .species(dto.getSpecies())
                .breed(dto.getBreed())
                .age(dto.getAge())
                .build();
    }

    public static PetResponseDto toResponse(Pet pet) {
        if (pet == null) return null;

        return PetResponseDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .breed(pet.getBreed())
                .age(pet.getAge())
                .ownerId(pet.getOwner().getId())
                .build();
    }
}