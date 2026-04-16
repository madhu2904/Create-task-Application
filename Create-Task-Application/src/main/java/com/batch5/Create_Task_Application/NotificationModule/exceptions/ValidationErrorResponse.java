package com.batch5.Create_Task_Application.NotificationModule.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Data
public class ValidationErrorResponse {
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;
}
