package com.batch5.Create_Task_Application.CollaborationModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentRequestDTO {

    @NotBlank(message = "Comment text cannot be blank")
    private String text;

    @NotNull(message = "User ID is required")
    private Long userId;

    public CommentRequestDTO(String text, Long userId) {
        this.text = text;
        this.userId = userId;
    }

    public CommentRequestDTO() {
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
