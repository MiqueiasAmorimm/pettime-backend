package com.pettime.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Unique identifier for the appointment
    // Identifiant unique pour le rendez-vous

    // Pet associated with the appointment
    // Animal associé au rendez-vous
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    // PetShop that will provide the service
    // PetShop qui fournira le service
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petshop_id", nullable = false)
    private User petshop;

    // Appointment start time
    // Heure de début du rendez-vous
    @Column(nullable = false)
    private LocalDateTime startTime;

    // Appointment end time
    // Heure de fin du rendez-vous
    @Column(nullable = false)
    private LocalDateTime endTime;

    // Appointment status
    // Statut du rendez-vous
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    // Payment status: true = 100%, false = 50% or not paid yet
    // Statut du paiement : true = 100%, false = 50% ou non payé
    @Column(nullable = false)
    private Boolean paid;

    // Automatically sets creation timestamp
    // Horodatage de création automatique
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Automatically updates timestamp on modification
    // Mise à jour automatique de l'horodatage lors de la modification
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Enum for appointment status
    // Enum pour le statut du rendez-vous
    public enum AppointmentStatus {
        SCHEDULED,   // Appointment is scheduled / Rendez-vous prévu
        CANCELLED,   // Appointment is cancelled / Rendez-vous annulé
        COMPLETED    // Appointment has been completed / Rendez-vous terminé
    }
}
