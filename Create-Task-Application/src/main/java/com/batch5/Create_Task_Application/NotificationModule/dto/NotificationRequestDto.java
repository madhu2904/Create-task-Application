package com.batch5.Create_Task_Application.NotificationModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequestDto {

    @NotBlank(message = "text can't be empty")
    private String text;


    @NotNull(message = "user id is required")
    private Long userId;

    public NotificationRequestDto() {
    }

    public NotificationRequestDto(String text, Long userId) {
        this.text = text;
        this.userId = userId;
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
}