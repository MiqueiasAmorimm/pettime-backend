package com.pettime.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequestDto {

    @NotNull
    private Long petId;

    @NotNull
    private Long petshopId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
}
