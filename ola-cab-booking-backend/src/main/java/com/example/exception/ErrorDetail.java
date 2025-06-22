package com.example.exception;

import java.time.LocalDateTime;

public class ErrorDetail {

    private String error;
    private String details;
    private LocalDateTime timestamp;

    public ErrorDetail(String error, String details, LocalDateTime timestamp) {
        this.error = error;
        this.details = details;
        this.timestamp = timestamp;
    }
}
