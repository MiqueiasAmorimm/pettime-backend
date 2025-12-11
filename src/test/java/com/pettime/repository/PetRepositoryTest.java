package com.pettime.repository;

import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.model.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("✔ Should return all pets belonging to a specific owner")
    void shouldFindPetsByOwner() {

        User owner = userRepository.save(
                User.builder()
                        .name("Jean Dupont")
                        .email("jean@client.ca")
                        .password("123")
                        .role(UserRole.CLIENT)
                        .build()
        );

        User anotherOwner = userRepository.save(
                User.builder()
                        .name("Marie Tremblay")
                        .email("marie@client.ca")
                        .password("123")
                        .role(UserRole.CLIENT)
                        .build()
        );

        petRepository.save(
                Pet.builder()
                        .name("Rex")
                        .species("Dog")
                        .breed("Labrador")
                        .owner(owner)
                        .build()
        );

        petRepository.save(
                Pet.builder()
                        .name("Mia")
                        .species("Cat")
                        .breed("Siamese")
                        .owner(owner)
                        .build()
        );

        petRepository.save(
                Pet.builder()
                        .name("Léo")
                        .species("Dog")
                        .breed("Husky")
                        .owner(anotherOwner)
                        .build()
        );

        List<Pet> result = petRepository.findByOwner(owner);

        assertThat(result)
                .hasSize(2)
                .extracting(Pet::getName)
                .containsExactlyInAnyOrder("Rex", "Mia");
    }

    @Test
    @DisplayName("✔ Should return empty list when owner has no pets")
    void shouldReturnEmptyWhenOwnerHasNoPets() {

        User owner = userRepository.save(
                User.builder()
                        .name("Isabelle Gagnon")
                        .email("isa@client.ca")
                        .password("123")
                        .role(UserRole.CLIENT)
                        .build()
        );

        List<Pet> result = petRepository.findByOwner(owner);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("✔ Should isolate pets by owner even when names are identical")
    void shouldIsolateByOwnerDespiteSimilarNames() {

        User ownerA = userRepository.save(
                User.builder()
                        .name("Owner A")
                        .email("a@client.ca")
                        .password("123")
                        .role(UserRole.CLIENT)
                        .build()
        );

        User ownerB = userRepository.save(
                User.builder()
                        .name("Owner B")
                        .email("b@client.ca")
                        .password("123")
                        .role(UserRole.CLIENT)
                        .build()
        );

        petRepository.save(
                Pet.builder()
                        .name("Buddy")
                        .species("Dog")
                        .breed("Golden")
                        .owner(ownerA)
                        .build()
        );

        petRepository.save(
                Pet.builder()
                        .name("Buddy")
                        .species("Dog")
                        .breed("Poodle")
                        .owner(ownerB)
                        .build()
        );

        List<Pet> result = petRepository.findByOwner(ownerA);

        assertThat(result)
                .hasSize(1)
                .extracting(Pet::getBreed)
                .containsExactly("Golden");
    }
}
