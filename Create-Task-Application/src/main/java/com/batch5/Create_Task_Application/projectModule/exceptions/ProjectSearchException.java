package com.batch5.Create_Task_Application.projectModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class ProjectSearchException extends NotFoundException {

    public ProjectSearchException(String keyword) {
        super("No projects found matching keyword: " + keyword);
    }
}