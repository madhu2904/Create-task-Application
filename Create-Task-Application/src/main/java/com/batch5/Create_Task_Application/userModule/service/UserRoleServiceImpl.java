package com.batch5.Create_Task_Application.userModule.service;

import com.batch5.Create_Task_Application.userModule.entity.UserRole;
import com.batch5.Create_Task_Application.userModule.exceptions.InvalidUserDataException;
import com.batch5.Create_Task_Application.userModule.exceptions.RoleNotFoundException;
import com.batch5.Create_Task_Application.userModule.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository roleRepository;

    //  CREATE ROLE
    @Override
    public UserRole createRole(UserRole role) {

        //  Validation
        if (role.getRoleName() == null || role.getRoleName().isBlank()) {
            throw new InvalidUserDataException("Role name cannot be empty");
        }

        return roleRepository.save(role);
    }

    //  GET ALL ROLES
    @Override
    public List<UserRole> getAllRoles() {
        return roleRepository.findAll();
    }

    //  GET ROLE BY ID
    @Override
    public UserRole getRoleById(Integer roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + roleId));
    }

    //  DELETE ROLE
    @Override
    public void deleteRole(Integer roleId) {
        UserRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + roleId));

        roleRepository.delete(role);
    }
}