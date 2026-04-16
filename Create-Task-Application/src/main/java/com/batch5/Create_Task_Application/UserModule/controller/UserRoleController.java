package com.batch5.Create_Task_Application.UserModule.controller;

import com.batch5.Create_Task_Application.UserModule.entity.UserRole;
import com.batch5.Create_Task_Application.UserModule.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService roleService;

    //  CREATE ROLE
    @PostMapping
    public UserRole createRole(@RequestBody UserRole role) {
        return roleService.createRole(role);
    }

    //  GET ALL ROLES
    @GetMapping
    public List<UserRole> getAllRoles() {
        return roleService.getAllRoles();
    }

    //  GET ROLE BY ID
    @GetMapping("/{roleId}")
    public UserRole getRoleById(@PathVariable Long roleId) {
        return roleService.getRoleById(roleId);
    }

    //  DELETE ROLE
    @DeleteMapping("/{roleId}")
    public String deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return "Role deleted successfully";
    }
}