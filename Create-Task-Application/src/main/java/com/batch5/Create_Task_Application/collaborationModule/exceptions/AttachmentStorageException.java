package com.batch5.Create_Task_Application.collaborationModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.BadRequestException;

public class AttachmentStorageException extends BadRequestException {
    public AttachmentStorageException(String message) {
        super(message);
    }

}
