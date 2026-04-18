package com.batch5.Create_Task_Application.taskModule.exceptions;

public class CategoryNotMappedException extends RuntimeException{
    public CategoryNotMappedException(String msg){
        super(msg);
    }
}
