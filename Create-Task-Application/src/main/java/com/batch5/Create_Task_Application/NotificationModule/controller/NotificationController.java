package com.batch5.Create_Task_Application.NotificationModule.controller;

import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationRequestDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationResponseDto;
import com.batch5.Create_Task_Application.NotificationModule.entity.Notification;
import com.batch5.Create_Task_Application.NotificationModule.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        NotificationResponseDto response =notificationService.getNotification(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}