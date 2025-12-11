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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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
    void setup() {

        petshop = User.builder()
                .id(10L)
                .name("PetShop Québec")
                .email("contact@pettime.ca")
                .password("123")
                .role(UserRole.PETSHOP)
                .build();

        pet = Pet.builder()
                .id(20L)
                .name("Buddy")
                .species("Dog")
                .breed("Golden Retriever")
                .owner(
                        User.builder()
                                .id(30L)
                                .name("Jean Dupont")
                                .email("jean@client.ca")
                                .password("123")
                                .role(UserRole.CLIENT)
                                .build()
                )
                .build();
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("✅ Should create appointment when valid and has no conflicts")
    void shouldCreateAppointmentSuccessfully() {

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

        // no overlap
        when(appointmentRepository.findOverlappingAppointments(
                petshop.getId(), start, end
        )).thenReturn(Collections.emptyList());

        when(appointmentRepository.save(any())).thenReturn(appointment);

        Appointment result = appointmentService.create(appointment);

        assertThat(result).isNotNull();
        assertThat(result.getPetshop().getName()).isEqualTo("PetShop Québec");

        verify(appointmentRepository, times(1)).save(any());
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("🚫 Should throw when appointment time is invalid")
    void shouldRejectInvalidTimeOrder() {

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(LocalDateTime.now().plusHours(2))
                .endTime(LocalDateTime.now().plusHours(1)) // end < start
                .build();

        assertThatThrownBy(() -> appointmentService.create(appointment))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("endTime must be after startTime");

        verify(appointmentRepository, never()).save(any());
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("🚫 Should prevent overlapping appointments")
    void shouldPreventOverlappingAppointments() {

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(1);

        Appointment request = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(start)
                .endTime(end)
                .build();

        // simulate conflict
        when(appointmentRepository.findOverlappingAppointments(
                petshop.getId(), start, end
        )).thenReturn(List.of(new Appointment()));

        assertThatThrownBy(() -> appointmentService.create(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Conflicting appointment exists");

        verify(appointmentRepository, never()).save(any());
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("🚫 Should throw PetshopInactiveException when petshop email contains 'inactive'")
    void shouldThrowWhenPetshopIsInactive() {

        petshop.setEmail("inactive-petshop@pettime.ca");

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();

        when(appointmentRepository.findOverlappingAppointments(anyLong(), any(), any()))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> appointmentService.create(appointment))
                .isInstanceOf(PetshopInactiveException.class)
                .hasMessageContaining("petshop-slug");

        verify(appointmentRepository, never()).save(any());
    }
}
