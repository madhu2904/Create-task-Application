package com.batch5.Create_Task_Application.CollaborationModule.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException(String message, Throwable cause) {

        super(message, cause);
    }
}
