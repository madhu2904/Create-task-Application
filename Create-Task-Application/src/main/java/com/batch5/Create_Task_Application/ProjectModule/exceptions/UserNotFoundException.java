package com.batch5.Create_Task_Application.ProjectModule.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User not found "+userId);
    }
}
