package com.batch5.Create_Task_Application.CollaborationModule.exceptions;

public class AttachmentStorageException extends RuntimeException {
    public AttachmentStorageException(String message) {
        super(message);
    }

    public AttachmentStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
