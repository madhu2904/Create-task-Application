package com.batch5.Create_Task_Application.collaborationModule.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
}