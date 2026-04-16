package com.batch5.Create_Task_Application.UserModule.service;

import com.batch5.Create_Task_Application.UserModule.entity.UserRole;

import java.util.List;

public interface UserRoleService {

    UserRole createRole(UserRole role);

    List<UserRole> getAllRoles();

    UserRole getRoleById(Long roleId);

    void deleteRole(Long roleId);
}