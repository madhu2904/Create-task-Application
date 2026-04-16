package com.batch5.Create_Task_Application.NotificationModule.controller;

import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationDto;
import com.batch5.Create_Task_Application.NotificationModule.entity.Notification;
import com.batch5.Create_Task_Application.NotificationModule.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public Notification addNotification(@Valid @RequestBody NotificationDto dto) {
        return notificationService.addNotification(dto);
    }
}