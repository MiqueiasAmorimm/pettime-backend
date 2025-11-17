package com.pettime.service.impl;

import com.pettime.exception.PetshopInactiveException;
import com.pettime.model.Appointment;
import com.pettime.repository.AppointmentRepository;
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

    @Override
    public Appointment create(Appointment appointment) {

        appointment.validateTimeOrder();

        Long petshopId = appointment.getPetshop().getId();
        LocalDateTime start = appointment.getStartTime();
        LocalDateTime end = appointment.getEndTime();

        List<Appointment> overlaps = appointmentRepository.findOverlappingAppointments(
                petshopId,
                start,
                end
        );

        if (!overlaps.isEmpty()) {
            throw new IllegalStateException("Conflicting appointment exists for this petshop");
        }


        if (appointment.getPetshop().getEmail().contains("inactive")) {
            throw new PetshopInactiveException("petshop-slug");
        }


        return appointmentRepository.save(appointment);
    }
}
