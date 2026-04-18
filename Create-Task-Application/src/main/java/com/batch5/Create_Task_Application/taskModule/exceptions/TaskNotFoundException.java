package com.batch5.Create_Task_Application.taskModule.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String msg){
        super(msg);
    }
}
