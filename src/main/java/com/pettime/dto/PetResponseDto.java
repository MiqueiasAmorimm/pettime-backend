package com.pettime.dto;

import com.pettime.model.Pet;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetResponseDto {

    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;

    private Long ownerId;
    private String ownerName;

    public static PetResponseDto fromEntity(Pet pet) {
        if (pet == null) {
            return null;
        }

        return PetResponseDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .breed(pet.getBreed())
                .age(pet.getAge())
                .ownerId(pet.getOwner() != null ? pet.getOwner().getId() : null)
                .ownerName(pet.getOwner() != null ? pet.getOwner().getName() : null)
                .build();
    }
}