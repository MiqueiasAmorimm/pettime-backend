package com.pettime.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Standard API error response model used across all exception handlers.
 * (FR) Modèle standard de réponse d’erreur API utilisé dans tous les gestionnaires d’exceptions.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    /**
     * Timestamp when the error occurred.
     * (FR) Horodatage de l’erreur.
     */
    private Instant timestamp = Instant.now();

    /**
     * HTTP status code (e.g., 404, 500, etc.).
     * (FR) Code d’état HTTP (ex : 404, 500, etc.).
     */
    private int status;

    /**
     * Error title or category (e.g., "Not Found", "Bad Request").
     * (FR) Titre ou catégorie de l’erreur (ex : "Non trouvé", "Requête invalide").
     */
    private String error;

    /**
     * Detailed error message for debugging or user feedback.
     * (FR) Message d’erreur détaillé pour le débogage ou le retour utilisateur.
     */
    private String message;

    /**
     * Request path where the error occurred.
     * (FR) Chemin de la requête où l’erreur s’est produite.
     */
    private String path;

    /**
     * Convenience constructor using HttpStatus.
     * (FR) Constructeur pratique utilisant HttpStatus.
     */
    public ApiError(HttpStatus status, String message, String path) {
        this.timestamp = Instant.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}
