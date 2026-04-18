package com.batch5.Create_Task_Application.taskModule.exceptions;

public class DataConflictException extends RuntimeException{
    public DataConflictException(String msg){
        super(msg);
    }
}
