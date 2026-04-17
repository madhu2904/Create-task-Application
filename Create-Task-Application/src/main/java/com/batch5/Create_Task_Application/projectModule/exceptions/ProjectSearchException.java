package com.batch5.Create_Task_Application.projectModule.exceptions;

public class ProjectSearchException extends RuntimeException {

    public ProjectSearchException(String keyword) {
        super("No projects found matching keyword: " + keyword);
    }
}