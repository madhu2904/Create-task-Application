package com.batch5.Create_Task_Application.CollaborationModule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {

    private Integer commentId;
    private String text;
    private LocalDateTime createdAt;
    private Integer taskId;
    private Long userId;

}
