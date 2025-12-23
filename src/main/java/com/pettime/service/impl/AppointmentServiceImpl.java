package com.pettime.service.impl;

import com.pettime.exception.ResourceNotFoundException;
import com.pettime.model.Appointment;
import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.model.AppointmentStatus;
import com.pettime.repository.AppointmentRepository;
import com.pettime.repository.PetRepository;
import com.pettime.repository.UserRepository;
import com.pettime.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of AppointmentService.
 * (FR) Implémentation du service de rendez-vous.
 */
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Override
    public Appointment create(
            Long petId,
            Long petshopId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        // 1️⃣ Validate time window
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }

        // 2️⃣ Load pet
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        // 3️⃣ Load petshop
        User petshop = userRepository.findById(petshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Petshop not found"));

        // 5️⃣ Check overlapping appointments
        List<Appointment> overlaps =
                appointmentRepository.findOverlappingAppointments(
                        petshopId,
                        startTime,
                        endTime
                );

        if (!overlaps.isEmpty()) {
            throw new IllegalStateException(
                    "Conflicting appointment exists for this petshop"
            );
        }

        // 6️⃣ Build appointment (domain invariant enforced here)
        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(startTime)
                .endTime(endTime)
                .status(AppointmentStatus.PENDING)
                .paid(false)
                .build();

        // 7️⃣ Persist
        return appointmentRepository.save(appointment);
    }
}
