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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 🧪 Comprehensive tests for AppointmentRepository.
 * (FR) Tests complets pour AppointmentRepository.
 *
 * Covers:
 * - Persistence and basic retrieval
 * - Overlapping appointment detection
 * - Edge cases for no overlap
 * - Ensures database constraints work as expected
 */
@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository; // ✅ era Appointment

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    // =====================================================================
    // Helpers
    // =====================================================================

    /**
     * Helper method to persist a User.
     */
    private User createUser(String name, String email, UserRole role) {
        return userRepository.save(
                User.builder()
                        .name(name)
                        .email(email)
                        .password("123")
                        .role(role)
                        .build()
        );
    }

    /**
     * Helper method to persist a Pet.
     */
    private Pet createPet(String name, User owner) {
        return petRepository.save(
                Pet.builder()
                        .name(name)
                        .species("Dog")
                        .breed("Golden Retriever")
                        .owner(owner)
                        .build()
        );
    }

    /**
     * Helper method to persist an Appointment.
     */
    private Appointment createAppointment(Pet pet, User petshop, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.save(
                Appointment.builder()
                        .pet(pet)
                        .petshop(petshop)
                        .startTime(start)
                        .endTime(end)
                        .status(Appointment.AppointmentStatus.SCHEDULED)
                        .paid(false)
                        .build()
        );
    }

    // =====================================================================
    // ✔ BASICS: Persist and Retrieve
    // =====================================================================

    @Test
    @DisplayName("✅ Should persist and retrieve appointment by ID")
    void shouldPersistAndRetrieveAppointment() {
        User petshop = createUser("PetShop Québec", "shop@pettime.ca", UserRole.PETSHOP);
        User client = createUser("Jean Dupont", "jean@client.ca", UserRole.CLIENT);
        Pet pet = createPet("Buddy", client);

        Appointment saved = createAppointment(
                pet,
                petshop,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2)
        );

        Appointment found = appointmentRepository.findById(saved.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getPetshop().getName()).isEqualTo("PetShop Québec");
        assertThat(found.getStartTime()).isBefore(found.getEndTime());
    }

    // =====================================================================
    // ✔ OVERLAP DETECTION
    // =====================================================================

    @Test
    @DisplayName("🚫 Should detect overlapping appointments")
    void shouldDetectOverlappingAppointments() {

        User petshop = createUser("PetShop Québec", "shop@pettime.ca", UserRole.PETSHOP);
        User client = createUser("Jean Dupont", "jean@client.ca", UserRole.CLIENT);
        Pet pet = createPet("Rex", client);

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);

        createAppointment(pet, petshop, start, end);

        List<Appointment> overlap = appointmentRepository.findOverlappingAppointments(
                petshop.getId(),
                start.plusMinutes(30),
                end.plusMinutes(30)
        );

        assertThat(overlap).isNotEmpty();
    }

    // =====================================================================
    // ✔ NO-OVERLAP CASE
    // =====================================================================

    @Test
    @DisplayName("✅ Should NOT detect overlap when appointments don't overlap")
    void shouldNotDetectOverlap() {

        User petshop = createUser("PetShop Québec", "shop@pettime.ca", UserRole.PETSHOP);
        User client = createUser("Jean Dupont", "jean@client.ca", UserRole.CLIENT);
        Pet pet = createPet("Rex", client);

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);

        createAppointment(pet, petshop, start, end);

        List<Appointment> overlap = appointmentRepository.findOverlappingAppointments(
                petshop.getId(),
                end.plusMinutes(1),
                end.plusHours(1)
        );

        assertThat(overlap).isEmpty();
    }

    // =====================================================================
    // ✔ MULTIPLE APPOINTMENTS
    // =====================================================================

    @Test
    @DisplayName("🔄 Should detect at least one overlapping appointment among multiple")
    void shouldDetectClosestOverlappingAppointment() {

        User petshop = createUser("PetShop Québec", "shop@pettime.ca", UserRole.PETSHOP);
        User client = createUser("Jean Dupont", "jean@client.ca", UserRole.CLIENT);
        Pet pet = createPet("Buddy", client);

        LocalDateTime now = LocalDateTime.now().plusDays(1);

        // A1: [now, now+1h]
        createAppointment(pet, petshop, now, now.plusHours(1));
        // A2: [now+2h, now+3h]
        createAppointment(pet, petshop, now.plusHours(2), now.plusHours(3));
        // A3: [now+4h, now+5h]
        createAppointment(pet, petshop, now.plusHours(4), now.plusHours(5));

        List<Appointment> overlap = appointmentRepository.findOverlappingAppointments(
                petshop.getId(),
                now.plusMinutes(30),
                now.plusHours(2)
        );

        assertThat(overlap).isNotEmpty();

        assertThat(overlap)
                .anySatisfy(appt -> assertThat(appt.getStartTime()).isEqualTo(now));
    }
}
