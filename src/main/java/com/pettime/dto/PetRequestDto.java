package com.pettime.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must have at most 100 characters")
    private String name;

    @NotBlank(message = "Species is required")
    @Size(max = 50, message = "Species must have at most 50 characters")
    private String species;

    @Size(max = 100, message = "Breed must have at most 100 characters")
    private String breed;

    @Min(value = 0, message = "Age must be zero or greater")
    private Integer age;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;
}