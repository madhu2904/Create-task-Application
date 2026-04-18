package com.batch5.Create_Task_Application.taskModule.exceptions;

public class InvalidTaskException extends RuntimeException{
    public InvalidTaskException(String msg){
        super(msg);
    }
}
