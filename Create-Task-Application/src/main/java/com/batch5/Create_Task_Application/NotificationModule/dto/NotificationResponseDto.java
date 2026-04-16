package com.batch5.Create_Task_Application.NotificationModule.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationResponseDto {

    private String text;
    private Long userId;
    private boolean status;
}