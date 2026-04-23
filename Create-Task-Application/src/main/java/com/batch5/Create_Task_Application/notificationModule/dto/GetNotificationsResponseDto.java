package com.batch5.Create_Task_Application.notificationModule.dto;

import java.time.LocalDateTime;

public class GetNotificationsResponseDto {

    private int notificationId;
    private String text;
    private LocalDateTime dateTime;
    private boolean isRead;

    public GetNotificationsResponseDto(int notificationId, String text, LocalDateTime dateTime) {
        this.notificationId = notificationId;
        this.text = text;
        this.dateTime = dateTime;
    }


    public GetNotificationsResponseDto(int notificationId, String text, LocalDateTime dateTime, boolean isRead) {
        this.notificationId = notificationId;
        this.text = text;
        this.dateTime = dateTime;
        this.isRead = isRead;
    }


    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}