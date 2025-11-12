package com.pettime.repository;

import com.pettime.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.petshop.id = :petshopId
        AND a.startTime < :endTime
        AND a.endTime > :startTime
    """)
    Optional<Appointment> findOverlappingAppointments(
            @Param("petshopId") Long petshopId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
