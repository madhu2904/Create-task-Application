package com.batch5.Create_Task_Application.userModule.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;                    // ← holds any DTO inside
    private LocalDateTime timestamp;

    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
}