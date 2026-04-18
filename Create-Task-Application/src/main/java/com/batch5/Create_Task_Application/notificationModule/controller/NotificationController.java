package com.batch5.Create_Task_Application.notificationModule.controller;

import com.batch5.Create_Task_Application.notificationModule.dto.GetNotificationsResponseDto;
import com.batch5.Create_Task_Application.notificationModule.dto.NotificationRequestDto;
import com.batch5.Create_Task_Application.notificationModule.dto.NotificationResponseDto;
import com.batch5.Create_Task_Application.notificationModule.dto.ResponseStructureDto;
import com.batch5.Create_Task_Application.notificationModule.service.NotificationService;
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
    public ResponseEntity addNotification(@Valid @RequestBody NotificationRequestDto dto) {
        NotificationResponseDto response = notificationService.addNotification(dto);
        ResponseStructureDto<NotificationResponseDto> responseStructure=new ResponseStructureDto(
                HttpStatus.CREATED.value(),

                "Notification Added Successfully",
                response,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity getNotification(@PathVariable int notificationId)
    {
        NotificationResponseDto response =notificationService.getNotificationById(notificationId);
        ResponseStructureDto<NotificationResponseDto> responseStructure=new ResponseStructureDto(
                HttpStatus.OK.value(),
                "Retrived the Notification Successfully",
                response,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(responseStructure, HttpStatus.OK);

    }

    @GetMapping("/users/{userId}/notifications")
    public  ResponseEntity getNotification(@PathVariable long userId)
    {
        List<GetNotificationsResponseDto> response=notificationService.getNotificationsByUserId(userId);
        ResponseStructureDto<List<GetNotificationsResponseDto>> responseStructure=new ResponseStructureDto(
                HttpStatus.OK.value(),
                "Retrived notifications of the user successfully",
                response,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(responseStructure, HttpStatus.OK);

    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ResponseStructureDto<String>> deleteNotification(@PathVariable int notificationId) {

        notificationService.deleteNotificationById(notificationId);

        ResponseStructureDto<String> responseStructure =
                new ResponseStructureDto<>(
                        HttpStatus.OK.value(),
                        "Notification deleted successfully",
                        null,
                        LocalDateTime.now()
                );

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/notifications/unread")
    public ResponseEntity getUnreadNotifications(@PathVariable long userId)
    {

        List<GetNotificationsResponseDto> response=notificationService.getUnreadNotificationsByUserId(userId);
        ResponseStructureDto<List<GetNotificationsResponseDto>> responseStructure=new ResponseStructureDto(
                HttpStatus.OK.value(),
                "Retrived notifications of the user successfully",
                response,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(responseStructure, HttpStatus.OK);

    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity markNotificationAsRead(@PathVariable int notificationId)
    {
        notificationService.markNotificationAsRead(notificationId);
        ResponseStructureDto<String> responseStructure =
                new ResponseStructureDto<>(
                        HttpStatus.OK.value(),
                        "Marked Notification As read Successfully",
                        null,
                        LocalDateTime.now()
                );

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }
}