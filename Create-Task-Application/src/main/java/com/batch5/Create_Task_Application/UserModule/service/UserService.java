package com.batch5.Create_Task_Application.UserModule.service;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import java.util.List;
public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
