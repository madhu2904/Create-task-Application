package com.batch5.Create_Task_Application.collaborationModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class AttachmentNotFoundException extends NotFoundException {
    public AttachmentNotFoundException(String message) {
        super(message);
    }


}
