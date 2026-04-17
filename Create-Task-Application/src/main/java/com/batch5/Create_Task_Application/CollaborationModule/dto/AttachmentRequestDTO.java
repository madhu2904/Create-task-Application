package com.batch5.Create_Task_Application.CollaborationModule.dto;

import jakarta.validation.constraints.NotBlank;

public class AttachmentRequestDTO {

    @NotBlank(message = "File name cannot be empty")
    private String fileName;

    @NotBlank(message = "File path cannot be empty")
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
