package com.batch5.Create_Task_Application.CollaborationModule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
public class CommentResponseDTO {

    private Integer commentId;
    private String text;
    private LocalDateTime createdAt;
    private Integer taskId;
    private Long userId;

    public CommentResponseDTO(Integer commentId, String text, LocalDateTime createdAt, Integer taskId, Long userId) {
        this.commentId = commentId;
        this.text = text;
        this.createdAt = createdAt;
        this.taskId = taskId;
        this.userId = userId;
    }

    public CommentResponseDTO() {
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
