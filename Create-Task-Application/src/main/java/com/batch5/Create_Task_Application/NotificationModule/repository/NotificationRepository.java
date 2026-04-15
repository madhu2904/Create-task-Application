package com.batch5.Create_Task_Application.NotificationModule.repository;

import com.batch5.Create_Task_Application.NotificationModule.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
}
