package com.batch5.Create_Task_Application.notificationModule.dto;


public class NotificationResponseDto {

    private String text;
    private Long userId;
    private boolean isRead;

    public NotificationResponseDto(String text, Long userId, boolean isRead) {
        this.text = text;
        this.userId = userId;
        this.isRead = isRead;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }
}