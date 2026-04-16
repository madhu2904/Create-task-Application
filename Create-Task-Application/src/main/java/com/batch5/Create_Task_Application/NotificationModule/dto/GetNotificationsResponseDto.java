package com.batch5.Create_Task_Application.NotificationModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetNotificationsResponseDto
{
    private int notificationId;
    private String text;
    private LocalDateTime dateTime;
    private boolean isRead;


}
