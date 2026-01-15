package com.pettime.service.impl;


import com.pettime.exception.ResourceNotFoundException;
import com.pettime.model.Appointment;
import com.pettime.model.AppointmentStatus;
import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.repository.AppointmentRepository;
import com.pettime.repository.PetRepository;
import com.pettime.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Pet pet;
    private User petshop;

    @BeforeEach
    void setUp() {
        pet = Pet.builder().id(1L).build();
        petshop = User.builder().id(10L).build();
    }

    @Test
    void shouldCreateAppointmentWhenDataIsValid() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = start.plusHours(1);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(userRepository.findById(10L)).thenReturn(Optional.of(petshop));
        when(appointmentRepository.findOverlappingAppointments(10L, start, end))
                .thenReturn(List.of());
        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Appointment result = appointmentService.create(1L, 10L, start, end);

        assertNotNull(result);
        assertEquals(AppointmentStatus.PENDING, result.getStatus());
        assertFalse(result.getPaid());

        verify(petRepository).findById(1L);
        verify(userRepository).findById(10L);
        verify(appointmentRepository)
                .findOverlappingAppointments(10L, start, end);
        verify(appointmentRepository).save(any(Appointment.class));

    }

    @Test
    void shouldThrowWhenPetNotFound() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = start.plusHours(1);

        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> appointmentService.create(1L, 10L, start, end)
        );

        verify(petRepository).findById(1L);
        verifyNoInteractions(userRepository);
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenPetshopNotFound() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = start.plusHours(1);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> appointmentService.create(1L, 10L, start, end)
        );

        verify(petRepository).findById(1L);
        verify(userRepository).findById(10L);
        verifyNoInteractions(appointmentRepository);
    }

}
