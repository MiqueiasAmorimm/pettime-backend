package com.pettime.controller;

import com.pettime.dto.AppointmentRequestDto;
import com.pettime.dto.AppointmentResponseDto;
import com.pettime.model.Appointment;
import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Create a new appointment.
     * (FR) Crée un nouveau rendez-vous.
     */
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(
            @Valid @RequestBody AppointmentRequestDto request
    ) {
        log.info("Creating appointment for petId={} petshopId={}",
                request.getPetId(), request.getPetshopId());

        // 🔧 For portfolio purposes:
        // We create lightweight references instead of fetching from DB
        Pet pet = Pet.builder()
                .id(request.getPetId())
                .build();

        User petshop = User.builder()
                .id(request.getPetshopId())
                .build();

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .petshop(petshop)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .paid(false)
                .build();

        Appointment created = appointmentService.create(appointment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AppointmentResponseDto.fromEntity(created));
    }
}
