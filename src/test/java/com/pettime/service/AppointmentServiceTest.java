package com.pettime.service;

import com.pettime.exception.PetshopInactiveException;
import com.pettime.model.Appointment;
import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.model.UserRole;
import com.pettime.repository.AppointmentRepository;
import com.pettime.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 🧪 Unit tests for AppointmentServiceImpl.
 * Ensures business rules for creation, overlap prevention and inactive petshops.
 */
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

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
    @DisplayName("✅ Should create a valid appointment when no conflicts exist")
    void shouldCreateValidAppointment() {
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

        // No overlap
        when(appointmentRepository.findOverlappingAppointments(
                petshop.getId(), start, end
        )).thenReturn(List.of());

        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(appointment);

        Appointment created = appointmentService.create(appointment);

        assertThat(created).isNotNull();
        assertThat(created.getPetshop().getName()).isEqualTo("PetShop Québec");

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    @DisplayName("🚫 Should throw exception when PetShop is inactive")
    void shouldThrowWhenPetshopInactive() {
        String slug = "petshop-quebec";

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .build();

        doThrow(new PetshopInactiveException(slug))
                .when(appointmentRepository)
                .save(any(Appointment.class));

        assertThatThrownBy(() -> appointmentService.create(appointment))
                .isInstanceOf(PetshopInactiveException.class)
                .hasMessageContaining(slug);
    }

    @Test
    @DisplayName("🚫 Should prevent overlapping appointments")
    void shouldPreventOverlappingAppointments() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(1);

        Appointment newAppointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(start)
                .endTime(end)
                .build();

        // Simula conflito
        Appointment conflict = Appointment.builder().id(99L).build();

        when(appointmentRepository.findOverlappingAppointments(
                petshop.getId(), start, end
        )).thenReturn(List.of(conflict));

        assertThatThrownBy(() -> appointmentService.create(newAppointment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Conflicting appointment exists");

        verify(appointmentRepository, never()).save(any());
    }
}
