package com.batch5.Create_Task_Application.taskModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.BadRequestException;

public class InvalidCategoryException extends BadRequestException {
    public InvalidCategoryException(String msg){
        super(msg);
    }
}
