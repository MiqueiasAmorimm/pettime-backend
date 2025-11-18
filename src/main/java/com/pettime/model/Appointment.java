package com.pettime.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a scheduled appointment between a pet and a petshop.
 * (FR) Représente un rendez-vous planifié entre un animal et une animalerie.
 */
@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"pet", "petshop"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "petshop_id", nullable = false)
    private User petshop;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status;

    @Column(nullable = false)
    private Boolean paid;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Ensures endTime is always after startTime.
     * (FR) Vérifie que l'heure de fin est postérieure à l'heure de début.
     */
    public void validateTimeOrder() {
        if (startTime != null && endTime != null && endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("endTime must be after startTime");
        }
    }

    /**
     * Appointment status enum.
     */
    public enum AppointmentStatus {
        SCHEDULED,
        COMPLETED,
        CANCELLED
    }
}
