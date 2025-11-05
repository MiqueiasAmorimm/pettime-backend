package com.pettime.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 🧪 Unit tests for the Pet entity.
 * Ensures correct behavior of getters, setters, and builder logic.
 */
class PetTest {

    @Test
    void shouldCreatePetUsingBuilder() {
        User owner = User.builder()
                .id(1L)
                .name("Alice")
                .email("alice@test.com")
                .password("12345")
                .role(UserRole.CLIENT)
                .build();

        Pet pet = Pet.builder()
                .id(10L)
                .name("Rex")
                .species("Dog")
                .breed("Golden Retriever")
                .age(3)
                .owner(owner)
                .build();

        assertThat(pet.getId()).isEqualTo(10L);
        assertThat(pet.getName()).isEqualTo("Rex");
        assertThat(pet.getSpecies()).isEqualTo("Dog");
        assertThat(pet.getBreed()).isEqualTo("Golden Retriever");
        assertThat(pet.getAge()).isEqualTo(3);
        assertThat(pet.getOwner()).isNotNull();
        assertThat(pet.getOwner().getName()).isEqualTo("Alice");
    }

    @Test
    void shouldUpdatePetAttributes() {
        Pet pet = new Pet();
        pet.setName("Milo");
        pet.setSpecies("Cat");
        pet.setBreed("Siamese");
        pet.setAge(2);

        assertThat(pet.getName()).isEqualTo("Milo");
        assertThat(pet.getSpecies()).isEqualTo("Cat");
        assertThat(pet.getBreed()).isEqualTo("Siamese");
        assertThat(pet.getAge()).isEqualTo(2);
    }

    @Test
    void shouldHandleTimestamps() {
        Pet pet = new Pet();
        LocalDateTime now = LocalDateTime.now();
        pet.setCreatedAt(now);
        pet.setUpdatedAt(now.plusHours(1));

        assertThat(pet.getCreatedAt()).isBefore(pet.getUpdatedAt());
        assertThat(pet.getUpdatedAt()).isAfter(pet.getCreatedAt());
    }

    @Test
    void shouldLinkPetToOwner() {
        User owner = new User();
        owner.setId(5L);
        owner.setName("Bob");

        Pet pet = new Pet();
        pet.setOwner(owner);

        assertThat(pet.getOwner()).isNotNull();
        assertThat(pet.getOwner().getName()).isEqualTo("Bob");
    }
}
