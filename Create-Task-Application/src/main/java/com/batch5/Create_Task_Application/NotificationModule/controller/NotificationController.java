package com.batch5.Create_Task_Application.NotificationModule.controller;

import com.batch5.Create_Task_Application.NotificationModule.dto.GetNotificationsResponseDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationRequestDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationResponseDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.ResponseStructureDto;
import com.batch5.Create_Task_Application.NotificationModule.entity.Notification;
import com.batch5.Create_Task_Application.NotificationModule.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseStructureDto addNotification(@Valid @RequestBody NotificationRequestDto dto) {
        NotificationResponseDto response = notificationService.addNotification(dto);
        ResponseStructureDto<NotificationResponseDto> responseStructure=new ResponseStructureDto(
                HttpStatus.CREATED.value(),

                "Notification Added Successfully",
                response,
                LocalDateTime.now()

        );
        return responseStructure;
    }

    @GetMapping("/{notificationId}")
    public ResponseStructureDto getNotification(@PathVariable int notificationId)
    {
        NotificationResponseDto response =notificationService.getNotificationById(notificationId);
        ResponseStructureDto<NotificationResponseDto> responseStructure=new ResponseStructureDto(
                HttpStatus.OK.value(),
                "Retrived the Notification Successfully",
                response,
                LocalDateTime.now()

        );
        return responseStructure;
    }

    @GetMapping("/users/{userId}/notifications")
    public ResponseStructureDto getNotification(@PathVariable long userId)
    {
        List<GetNotificationsResponseDto> response=notificationService.getNotificationsByUserId(userId);
        ResponseStructureDto<List<GetNotificationsResponseDto>> responseStructure=new ResponseStructureDto(
                HttpStatus.OK.value(),
                "Retrived notifications of the user successfully",
                response,
                LocalDateTime.now()

        );
        return responseStructure;
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ResponseStructureDto<String>> deleteNotification(@PathVariable int notificationId) {

        notificationService.deleteNotificationById(notificationId);

        ResponseStructureDto<String> response =
                new ResponseStructureDto<>(
                        HttpStatus.OK.value(),
                        "Notification deleted successfully",
                        null,
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/notifications/unread")
    public ResponseStructureDto getUnreadNotifications(@PathVariable long userId)
    {

        List<GetNotificationsResponseDto> response=notificationService.getUnreadNotificationsByUserId(userId);
        ResponseStructureDto<List<GetNotificationsResponseDto>> responseStructure=new ResponseStructureDto(
                HttpStatus.OK.value(),
                "Retrived notifications of the user successfully",
                response,
                LocalDateTime.now()

        );
        return responseStructure;

    }

    @PutMapping("/{notificationId}/read")
    public ResponseStructureDto markNotificationAsRead(@PathVariable int notificationId)
    {
        notificationService.markNotificationAsRead(notificationId);
        ResponseStructureDto<String> response =
                new ResponseStructureDto<>(
                        HttpStatus.OK.value(),
                        "Marked Notification As read Successfully",
                        null,
                        LocalDateTime.now()
                );

        return response;
    }
}