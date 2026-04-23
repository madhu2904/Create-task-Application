package com.batch5.Create_Task_Application.collaborationModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class ResourceNotFoundException extends NotFoundException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
