package com.batch5.Create_Task_Application.userModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class RoleNotFoundException extends NotFoundException {
    public RoleNotFoundException(Integer id) {
        super("Role not found with ID: " + id, 404);
    }
    public RoleNotFoundException(String roleName) {
        super("Role not found with name: " + roleName, 404);
    }
}