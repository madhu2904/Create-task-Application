package com.batch5.Create_Task_Application.taskModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException(String msg){
        super(msg);
    }
}
