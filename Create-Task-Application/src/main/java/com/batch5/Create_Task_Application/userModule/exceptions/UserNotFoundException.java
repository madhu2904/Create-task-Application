package com.batch5.Create_Task_Application.userModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User not found with Id: " + id);
    }
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
