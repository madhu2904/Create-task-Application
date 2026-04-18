package com.batch5.Create_Task_Application.userModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.BadRequestException;

public class InvalidUserDataException extends BadRequestException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}