package com.batch5.Create_Task_Application.taskModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class NoDataFoundException extends NotFoundException {
    public NoDataFoundException(String msg){
        super(msg);
    }
}
