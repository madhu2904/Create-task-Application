package com.batch5.Create_Task_Application.userModule.service;

import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.entity.UserRole;
import com.batch5.Create_Task_Application.userModule.exceptions.*;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import com.batch5.Create_Task_Application.userModule.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    //  CREATE USER
    @Override
    public User createUser(User user) {

        //  Invalid Data Check
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new InvalidUserDataException("Username cannot be empty");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidUserDataException("Email cannot be empty");
        }

        // Duplicate User Check
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        return userRepository.save(user);
    }

    //  GET ALL USERS
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //  GET USER BY ID
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    //  UPDATE USER
    @Override
    public User updateUser(Long id, User user) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        //  Validation
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new InvalidUserDataException("Username cannot be empty");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidUserDataException("Email cannot be empty");
        }

        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setFullName(user.getFullName());
        existing.setPassword(user.getPassword());

        return userRepository.save(existing);
    }

    //  DELETE USER
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    @Override
    public UserRole createRole(UserRole role) {
        if (userRoleRepository.existsByRoleName(role.getRoleName())) {
            throw new InvalidUserDataException("Role already exists: " + role.getRoleName());
        }
        return userRoleRepository.save(role);
    }

    @Override
    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public void assignRoleToUser(Long userId, Integer roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        UserRole role = userRoleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));

        if (user.getRoles().contains(role)) {
            throw new InvalidUserDataException("Role already assigned to this user");
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(Long userId, Integer roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        UserRole role = userRoleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));

        if (!user.getRoles().contains(role)) {
            throw new InvalidUserDataException("Role not assigned to this user");
        }

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    @Override
    public List<UserRole> getRolesOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return user.getRoles();
    }


}