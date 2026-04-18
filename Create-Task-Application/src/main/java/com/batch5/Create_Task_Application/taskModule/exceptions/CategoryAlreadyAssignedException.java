package com.batch5.Create_Task_Application.taskModule.exceptions;

public class CategoryAlreadyAssignedException extends RuntimeException{
    public CategoryAlreadyAssignedException(String msg){
        super(msg);
    }
}
