package com.batch5.Create_Task_Application.UserModule.exceptions;

public class RoleNotFoundException extends AppException {
    public RoleNotFoundException(Long id) {
        super("Role not found with ID: " + id, 404);
    }
    public RoleNotFoundException(String roleName) {
        super("Role not found with name: " + roleName, 404);
    }
}