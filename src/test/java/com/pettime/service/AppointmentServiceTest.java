package com.pettime.service;

import com.pettime.exception.PetshopInactiveException;
import com.pettime.model.Appointment;
import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.model.UserRole;
import com.pettime.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 🧪 Unit tests for {@link AppointmentService}.
 * (FR) Tests unitaires pour {@link AppointmentService}.
 * Validates business logic for appointment creation, overlapping, and inactive petshops.
 * (FR) Valide la logique métier de création, de chevauchement et de gestion des animaleries inactives.
 */
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private User petshop;
    private Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        petshop = User.builder()
                .id(1L)
                .name("PetShop Québec")
                .email("shop@pettime.ca")
                .password("123")
                .role(UserRole.PETSHOP)
                .build();

        pet = Pet.builder()
                .id(2L)
                .name("Buddy")
                .species("Dog")
                .breed("Golden Retriever")
                .owner(User.builder()
                        .id(3L)
                        .name("Jean Dupont")
                        .email("jean@client.ca")
                        .password("123")
                        .role(UserRole.CLIENT)
                        .build())
                .build();
    }

    @Test
    @DisplayName("Should create a valid appointment when no conflicts exist")
    void shouldCreateValidAppointment() {
        // Arrange
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(1);

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(start)
                .endTime(end)
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .paid(false)
                .build();

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentRepository.findOverlappingAppointments(petshop.getId(), start, end))
                .thenReturn(Optional.empty());

        // Act
        Appointment created = appointmentService.create(appointment);

        // Assert
        assertThat(created).isNotNull();
        assertThat(created.getPetshop().getName()).isEqualTo("PetShop Québec");
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    @DisplayName("Should throw exception when PetShop is inactive")
    void shouldThrowWhenPetshopInactive() {
        // Arrange
        String slug = "petshop-quebec";
        petshop.setRole(UserRole.PETSHOP);

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .build();

        // Simula cenário de petshop inativo
        doThrow(new PetshopInactiveException(slug))
                .when(appointmentRepository).save(any(Appointment.class));

        // Act + Assert
        assertThatThrownBy(() -> appointmentService.create(appointment))
                .isInstanceOf(PetshopInactiveException.class)
                .hasMessageContaining("petshop with link '" + slug + "' is temporarily inactive");
    }

    @Test
    @DisplayName("Should prevent overlapping appointments for same PetShop")
    void shouldPreventOverlappingAppointments() {
        // Arrange
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(1);

        Appointment newAppointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(start)
                .endTime(end)
                .build();

        when(appointmentRepository.findOverlappingAppointments(petshop.getId(), start, end))
                .thenReturn(Optional.of(new Appointment())); // Simula conflito

        // Act + Assert
        assertThatThrownBy(() -> appointmentService.create(newAppointment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Conflicting appointment exists for this petshop");
    }
}
