package com.batch5.Create_Task_Application.commonModule.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
