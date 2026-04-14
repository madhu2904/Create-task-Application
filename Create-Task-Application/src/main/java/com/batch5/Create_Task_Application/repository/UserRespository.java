package com.batch5.Create_Task_Application.repository;
import com.batch5.Create_Task_Application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRespository extends JpaRepository<User, Long>  {
}
