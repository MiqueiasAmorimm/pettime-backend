package com.pettime.dto;

import com.pettime.model.Appointment;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponseDto {

    private Long id;
    private Long petId;
    private Long petshopId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private boolean paid;

    public static AppointmentResponseDto fromEntity(Appointment appointment) {
        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .petId(appointment.getPet().getId())
                .petshopId(appointment.getPetshop().getId())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .status(appointment.getStatus().name())
                .paid(appointment.getPaid())
                .build();
    }
}
