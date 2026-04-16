package com.batch5.Create_Task_Application.CollaborationModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDTO {

    @NotBlank(message = "Comment text cannot be blank")
    private String text;

    @NotNull(message = "User ID is required")
    private Long userId;

}
