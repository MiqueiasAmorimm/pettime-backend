package com.pettime.repository;

import com.pettime.model.Appointment;
import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.model.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 🧪 Tests for AppointmentRepository
 * (FR) Tests pour AppointmentRepository
 */
@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Test
    @DisplayName("✅ should persist and find appointment by ID | (FR) doit enregistrer et retrouver un rendez-vous par ID")
    void shouldPersistAndRetrieveAppointment() {
        // Arrange
        User petshop = userRepository.save(User.builder()
                .name("PetShop Québec")
                .email("shop@pettime.ca")
                .password("123")
                .role(UserRole.PETSHOP)
                .build());

        User client = userRepository.save(User.builder()
                .name("Jean Dupont")
                .email("jean@client.ca")
                .password("123")
                .role(UserRole.CLIENT)
                .build());

        Pet pet = petRepository.save(Pet.builder()
                .name("Buddy")
                .species("Dog")
                .breed("Golden Retriever")
                .owner(client)
                .build());

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .paid(false)
                .build();

        Appointment saved = repository.save(appointment);

        // Act
        Optional<Appointment> found = repository.findById(saved.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getPetshop().getName()).isEqualTo("PetShop Québec");
        assertThat(found.get().getStartTime()).isBefore(found.get().getEndTime());
    }

    @Test
    @DisplayName("🚫 should detect overlapping appointments | (FR) doit détecter les rendez-vous qui se chevauchent")
    void shouldDetectOverlappingAppointments() {
        // Arrange
        User petshop = userRepository.save(User.builder()
                .name("PetShop Québec")
                .email("shop@pettime.ca")
                .password("123")
                .role(UserRole.PETSHOP)
                .build());

        User client = userRepository.save(User.builder()
                .name("Jean Dupont")
                .email("jean@client.ca")
                .password("123")
                .role(UserRole.CLIENT)
                .build());

        Pet pet = petRepository.save(Pet.builder()
                .name("Rex")
                .species("Dog")
                .breed("Labrador")
                .owner(client)
                .build());

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);

        repository.save(Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(start)
                .endTime(end)
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .paid(false)
                .build());

        // Act
        Optional<Appointment> overlap = repository.findOverlappingAppointments(
                petshop.getId(),
                start.plusMinutes(30),
                end.plusMinutes(30)
        );

        // Assert
        assertThat(overlap).isPresent();
    }
}
