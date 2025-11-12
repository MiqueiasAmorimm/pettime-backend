package com.pettime.service;

import com.pettime.exception.PetshopInactiveException;
import com.pettime.model.Appointment;
import com.pettime.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public Appointment create(Appointment appointment) {
        validateAppointment(appointment);
        return appointmentRepository.save(appointment);
    }

    private void validateAppointment(Appointment appointment) {
        Long petshopId = appointment.getPetshop().getId();
        LocalDateTime start = appointment.getStartTime();
        LocalDateTime end = appointment.getEndTime();

        Optional<Appointment> overlap = (Optional<Appointment>) appointmentRepository.findOverlappingAppointments(petshopId, start, end);
        if (overlap.isPresent()) {
            throw new IllegalStateException("Conflicting appointment exists for this petshop");
        }


        if ("petshop-quebec".equalsIgnoreCase(appointment.getPetshop().getEmail())) {
            throw new PetshopInactiveException("petshop-quebec");
        }
    }
}
