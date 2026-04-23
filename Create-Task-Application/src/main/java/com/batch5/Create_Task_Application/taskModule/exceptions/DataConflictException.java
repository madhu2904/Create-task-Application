package com.batch5.Create_Task_Application.taskModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.ConflictException;

public class DataConflictException extends ConflictException {
    public DataConflictException(String msg){
        super(msg);
    }
}
