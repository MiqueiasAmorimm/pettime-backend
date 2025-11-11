package com.pettime.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class AppointmentValidationTest {

    @Test
    @DisplayName("Should validate time order and throw if endTime is before startTime")
    void shouldRejectInvalidEndTime() {
        // Arrange
        Appointment appointment = Appointment.builder()
                .startTime(LocalDateTime.of(2025, 11, 8, 14, 0))
                .endTime(LocalDateTime.of(2025, 11, 8, 13, 0))
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .paid(false)
                .build();

        // Act & Assert
        assertThatThrownBy(appointment::validateTimeOrder)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("endTime must be after startTime");
    }

    @Test
    @DisplayName("Should pass validation when endTime is after startTime")
    void shouldAcceptValidEndTime() {
        // Arrange
        Appointment appointment = Appointment.builder()
                .startTime(LocalDateTime.of(2025, 11, 8, 10, 0))
                .endTime(LocalDateTime.of(2025, 11, 8, 12, 0))
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .paid(true)
                .build();

        // Act & Assert
        appointment.validateTimeOrder();
        assertThat(appointment.getEndTime()).isAfter(appointment.getStartTime());
    }
}
