package com.pettime.exception;

/**
 * Thrown when an appointment conflicts with an existing one.
 * (FR) Lancée lorsqu’un rendez-vous entre en conflit avec un autre existant.
 */
public class AppointmentConflictException extends BusinessException {

    public AppointmentConflictException() {
        super("Conflicting appointment exists for this petshop");
    }

    public AppointmentConflictException(String message) {
        super(message);
    }
}
