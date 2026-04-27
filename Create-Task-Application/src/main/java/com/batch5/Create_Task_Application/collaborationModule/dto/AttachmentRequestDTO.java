package com.batch5.Create_Task_Application.collaborationModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AttachmentRequestDTO {

    @NotBlank(message = "File name cannot be empty")
    private String fileName;

    @NotBlank(message = "File path cannot be empty")
    @Pattern(
            regexp = "(?i).*?(\\.(jpg|png|pdf|txt|doc|docx|xlsx))?$",
            message = "Invalid file format"
    )
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public AttachmentRequestDTO(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public AttachmentRequestDTO() {
    }


}
