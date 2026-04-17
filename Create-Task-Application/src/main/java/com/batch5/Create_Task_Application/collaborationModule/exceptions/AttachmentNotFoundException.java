package com.batch5.Create_Task_Application.collaborationModule.exceptions;

public class AttachmentNotFoundException extends RuntimeException {
    public AttachmentNotFoundException(String message) {
        super(message);
    }

    public AttachmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
