package com.batch5.Create_Task_Application.TaskModule.exceptions;

public class CategoryAlreadyAssignedException extends Exception{
    public CategoryAlreadyAssignedException(String msg){
        super(msg);
    }
}
