package com.batch5.Create_Task_Application.CollaborationModule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentRequestDTO {

    @NotBlank(message = "File name cannot be empty")
    private String fileName;

    @NotBlank(message = "File path cannot be empty")
    private String filePath;

}
