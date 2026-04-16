package com.batch5.Create_Task_Application.NotificationModule.service;

import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationDto;
import com.batch5.Create_Task_Application.NotificationModule.entity.Notification;
import com.batch5.Create_Task_Application.NotificationModule.repository.NotificationRepository;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import com.batch5.Create_Task_Application.UserModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification addNotification(NotificationDto dto) {


        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Notification notification = new Notification();
        notification.setText(dto.getText());
        notification.setUser(user);


        return notificationRepository.save(notification);
    }
}
