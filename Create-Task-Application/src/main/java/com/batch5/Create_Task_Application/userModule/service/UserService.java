package com.batch5.Create_Task_Application.userModule.service;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.entity.UserRole;

import java.util.List;
import java.util.List;
public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
    // Role APIs
    UserRole createRole(UserRole role);
    List<UserRole> getAllRoles();

    // User-Role Mapping
    void assignRoleToUser(Long userId, Integer roleId);
    void removeRoleFromUser(Long userId, Integer roleId);
    List<UserRole> getRolesOfUser(Long userId);

}