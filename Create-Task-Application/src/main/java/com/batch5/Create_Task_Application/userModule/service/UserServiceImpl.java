package com.batch5.Create_Task_Application.userModule.service;

import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.exceptions.*;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // ✅ CREATE USER
    @Override
    public User createUser(User user) {

        // 🔴 Invalid Data Check
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new InvalidUserDataException("Username cannot be empty");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidUserDataException("Email cannot be empty");
        }

        // 🔴 Duplicate User Check
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
        }

        return userRepository.save(user);
    }

    // ✅ GET ALL USERS
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ GET USER BY ID
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    // ✅ UPDATE USER
    @Override
    public User updateUser(Long id, User user) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        // 🔴 Validation
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

    // ✅ DELETE USER
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}