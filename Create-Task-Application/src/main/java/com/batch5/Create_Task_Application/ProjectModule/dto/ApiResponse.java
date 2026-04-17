package com.batch5.Create_Task_Application.ProjectModule.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}