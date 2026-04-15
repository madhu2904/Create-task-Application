package com.batch5.Create_Task_Application.NotificationModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationDto
{
    @NotBlank(message="text can't be empty")
    private String text;

    @NotNull(message="user id is required")
    private Long userId;
}
