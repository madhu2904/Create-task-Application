package com.batch5.Create_Task_Application.taskModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.BadRequestException;

public class CategoryNotMappedException extends BadRequestException {
    public CategoryNotMappedException(String msg){
        super(msg);
    }
}
