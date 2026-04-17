package com.batch5.Create_Task_Application.userModule.exceptions;

public class InvalidUserDataException extends AppException {
    public InvalidUserDataException(String message) {
        super(message, 400);
    }
}