package com.batch5.Create_Task_Application.NotificationModule.service;

import com.batch5.Create_Task_Application.NotificationModule.dto.GetNotificationsResponseDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationRequestDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    NotificationResponseDto addNotification(NotificationRequestDto dto);

    NotificationResponseDto getNotificationById(int id);

    List<GetNotificationsResponseDto> getNotificationsByUserId(long userId);

    void deleteNotificationById(int notificationId);

    List<GetNotificationsResponseDto> getUnreadNotificationsByUserId(long userId);

    void markNotificationAsRead(int notificationId);
}