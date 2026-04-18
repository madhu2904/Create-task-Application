package com.batch5.Create_Task_Application.taskModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.ConflictException;

public class CategoryAlreadyAssignedException extends ConflictException {
    public CategoryAlreadyAssignedException(String msg){
        super(msg);
    }
}
