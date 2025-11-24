package com.pettime.service.impl;

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
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private User petshop;
    private Pet pet;

    @BeforeEach
    void setUp() {

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

        when(appointmentRepository.findOverlappingAppointments(
                petshop.getId(), start, end
        )).thenReturn(Collections.emptyList());

        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(appointment);

        Appointment created = appointmentService.create(appointment);

        assertThat(created).isNotNull();
        assertThat(created.getPetshop().getName()).isEqualTo("PetShop Québec");

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    @DisplayName("🚫 Should throw exception when Petshop is inactive")
    void shouldThrowWhenPetshopInactive() {

        petshop.setEmail("inactive@pettime.ca");

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .build();

        when(appointmentRepository.findOverlappingAppointments(anyLong(), any(), any()))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> appointmentService.create(appointment))
                .isInstanceOf(PetshopInactiveException.class)
                .hasMessageContaining("petshop-slug");
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

        when(appointmentRepository.findOverlappingAppointments(
                petshop.getId(), start, end
        )).thenReturn(List.of(new Appointment()));

        assertThatThrownBy(() -> appointmentService.create(newAppointment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Conflicting appointment exists");

        verify(appointmentRepository, never()).save(any());
    }
}
