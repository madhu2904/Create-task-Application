package com.batch5.Create_Task_Application.collaborationModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;
import org.hibernate.annotations.NotFound;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException(String message) {
        super(message);
    }


}
