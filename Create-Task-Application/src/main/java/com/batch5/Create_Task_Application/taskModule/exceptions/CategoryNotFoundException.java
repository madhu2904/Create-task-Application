package com.batch5.Create_Task_Application.taskModule.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String msg){
        super(msg);
    }
}
