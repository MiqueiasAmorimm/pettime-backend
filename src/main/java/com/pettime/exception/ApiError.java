package com.pettime.exception;

import java.time.Instant;

public class ApiError {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ApiError(Instant timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // getters e setters
}