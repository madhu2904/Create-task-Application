package com.batch5.Create_Task_Application.projectModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class ProjectNotFoundException extends NotFoundException {

    public ProjectNotFoundException(Integer projectId) {
        super("Project not found with id: " + projectId);
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}