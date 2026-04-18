package com.batch5.Create_Task_Application.userModule.exceptions;

public class UserAlreadyExistsException extends AppException {
    public UserAlreadyExistsException(String username) {
        super("User already exists with username: " + username, 409);
    }
}