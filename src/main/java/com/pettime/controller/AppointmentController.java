package com.pettime.controller;

import com.pettime.dto.AppointmentRequestDto;
import com.pettime.dto.AppointmentResponseDto;
import com.pettime.model.Appointment;
import com.pettime.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(
        name = "Appointments",
        description = "Scheduling operations for pet services"
)
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(
            summary = "Create appointment",
            description = "Creates a new appointment between a pet and a petshop"
    )
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(
            @RequestBody AppointmentRequestDto request
    ) {

        Appointment appointment = appointmentService.create(
                request.getPetId(),
                request.getPetshopId(),
                request.getStartTime(),
                request.getEndTime()
        );

        AppointmentResponseDto response =
                AppointmentResponseDto.fromEntity(appointment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
