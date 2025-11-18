package com.pettime.service;

import com.pettime.model.Appointment;

/**
 * Service contract for managing appointments.
 * (FR) Contrat de service pour la gestion des rendez-vous.
 */
public interface AppointmentService {

    /**
     * Create a new appointment applying business rules.
     * (FR) Crée un nouveau rendez-vous en appliquant les règles métier.
     */
    Appointment create(Appointment appointment);
}
