package com.batch5.Create_Task_Application.UserModule.exceptions;

public class AppException extends RuntimeException {
    private final int statusCode;

    public AppException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}