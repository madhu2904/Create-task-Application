package com.batch5.Create_Task_Application.taskModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.BadRequestException;

public class InvalidTaskException extends BadRequestException {
    public InvalidTaskException(String msg){
        super(msg);
    }
}
