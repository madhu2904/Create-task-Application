package com.batch5.Create_Task_Application.NotificationModule.repository;

import com.batch5.Create_Task_Application.NotificationModule.entity.Notification;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    List<Notification> findByUser(User user);

    @Query("SELECT n FROM Notification n WHERE n.user.userId = :userId AND n.isRead = false")
    List<Notification> findByUserAndIsReadFalse(@Param("userId") long userId);


}
