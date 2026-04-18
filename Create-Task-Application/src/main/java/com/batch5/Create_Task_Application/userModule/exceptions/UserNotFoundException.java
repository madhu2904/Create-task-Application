package com.batch5.Create_Task_Application.userModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User not found with ID: " + id);
    }
    public UserNotFoundException(String username) {
        super("User not found with username: " + username);
    }
}
