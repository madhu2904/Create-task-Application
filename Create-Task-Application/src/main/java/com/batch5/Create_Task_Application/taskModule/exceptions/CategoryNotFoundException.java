package com.batch5.Create_Task_Application.taskModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(String msg){
        super(msg);
    }
}
