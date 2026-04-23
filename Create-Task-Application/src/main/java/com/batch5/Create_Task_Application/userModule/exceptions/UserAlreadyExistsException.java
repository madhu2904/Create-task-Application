package com.batch5.Create_Task_Application.userModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(String username) {
        super("User already exists with username: " + username);
    }
}