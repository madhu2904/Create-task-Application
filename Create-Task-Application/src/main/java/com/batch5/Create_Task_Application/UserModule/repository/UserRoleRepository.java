package com.batch5.Create_Task_Application.UserModule.repository;
import com.batch5.Create_Task_Application.UserModule.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByRoleName(String roleName);
}
