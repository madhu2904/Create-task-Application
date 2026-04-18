package com.batch5.Create_Task_Application.collaborationModule.dto;

import java.time.LocalDateTime;

public class CollaborationDTO<T> {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public CollaborationDTO(int status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}