package com.batch5.Create_Task_Application.UserModule.exceptions;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(Long id) {
        super("User not found with ID: " + id, 404);
    }
    public UserNotFoundException(String username) {
        super("User not found with username: " + username, 404);
    }
}
