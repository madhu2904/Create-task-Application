package com.batch5.Create_Task_Application.userModule.service;

import com.batch5.Create_Task_Application.userModule.entity.UserRole;

import java.util.List;

public interface UserRoleService {

    UserRole createRole(UserRole role);

    List<UserRole> getAllRoles();

    UserRole getRoleById(Integer roleId);

    void deleteRole(Integer roleId);
}