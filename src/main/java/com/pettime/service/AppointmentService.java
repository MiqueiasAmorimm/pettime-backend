package com.pettime.service;

import com.pettime.model.Appointment;

import java.time.LocalDateTime;

/**
 * Appointment Domain Contract

 * Lifecycle:
 * - PENDING    : Appointment created, awaiting payment
 * - CONFIRMED  : Payment successfully confirmed
 * - CANCELLED  : Appointment cancelled by client or petshop
 * - COMPLETED  : Appointment finished after end time (optional future state)

 * Invariants:
 * - startTime must be strictly before endTime
 * - petId and petshopId must reference existing entities
 * - petshop must be active to accept new appointments
 * - No time overlap is allowed for appointments of the same petshop
 * - On creation:
 *      - status = PENDING
 *      - paid = false

 * All business rules related to appointments must be enforced
 * exclusively in the service layer.

 * (FR) Contrat de domaine pour la gestion des rendez-vous.
 */
public interface AppointmentService {

    /**
     * Creates a new appointment applying all business rules.
     *
     * @param petId       the pet identifier
     * @param petshopId   the petshop identifier
     * @param startTime   appointment start time
     * @param endTime     appointment end time
     * @return the created appointment
     *
     * (FR) Crée un nouveau rendez-vous en appliquant toutes les règles métier.
     */
    Appointment create(
            Long petId,
            Long petshopId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
