package com.batch5.Create_Task_Application.projectModule.exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Integer projectId) {
        super("Project not found with id: " + projectId);
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}