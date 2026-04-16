package com.batch5.Create_Task_Application.UserModule.repository;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
