package com.batch5.Create_Task_Application.NotificationModule.controller;

import com.batch5.Create_Task_Application.NotificationModule.dto.GetNotificationsResponseDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationRequestDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationResponseDto;
import com.batch5.Create_Task_Application.NotificationModule.entity.Notification;
import com.batch5.Create_Task_Application.NotificationModule.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity addNotification(@Valid @RequestBody NotificationRequestDto dto) {
        NotificationResponseDto response = notificationService.addNotification(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity getNotification(@PathVariable int notificationId)
    {
        NotificationResponseDto response =notificationService.getNotificationById(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity getNotification(@PathVariable long userId)
    {
        List<GetNotificationsResponseDto> response=notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity deleteNotification(@PathVariable int notificationId)
    {
        notificationService.deleteNotificationById(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body("Notification Deleted Successgully");
    }

    @GetMapping("/users/{userId}/notifications/unread")
    public ResponseEntity getUnreadNotifications(@PathVariable long userId)
    {
        List<GetNotificationsResponseDto> response=notificationService.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity markNotificationAsRead(@PathVariable int notificationId)
    {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body("Marked Notification As read");
    }
}