package com.batch5.Create_Task_Application.UserModule.service;

import com.batch5.Create_Task_Application.UserModule.entity.UserRole;
import com.batch5.Create_Task_Application.UserModule.exceptions.InvalidUserDataException;
import com.batch5.Create_Task_Application.UserModule.exceptions.RoleNotFoundException;
import com.batch5.Create_Task_Application.UserModule.repository.UserRoleRepository;
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
    public UserRole getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + roleId));
    }

    //  DELETE ROLE
    @Override
    public void deleteRole(Long roleId) {
        UserRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + roleId));

        roleRepository.delete(role);
    }
}