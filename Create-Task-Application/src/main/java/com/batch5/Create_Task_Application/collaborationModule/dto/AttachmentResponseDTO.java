package com.batch5.Create_Task_Application.collaborationModule.dto;

import lombok.Builder;

@Builder
public class AttachmentResponseDTO {

    private Integer attachmentId;
    private String fileName;
    private String filePath;
    private Integer taskId;

    public AttachmentResponseDTO(Integer attachmentId, String fileName, String filePath, Integer taskId) {
        this.attachmentId = attachmentId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.taskId = taskId;
    }

    public AttachmentResponseDTO() {
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
