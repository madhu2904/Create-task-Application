package com.batch5.Create_Task_Application.TaskModule.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String msg){
        super(msg);
    }
}
