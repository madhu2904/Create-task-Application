package com.batch5.Create_Task_Application.CollaborationModule.entity;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AttachmentID")
    private Integer attachmentId;

    @NotBlank(message = "File name cannot be empty")
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotBlank(message = "File path cannot be empty")
    @Column(name = "FilePath", nullable = false)
    private String filePath;

    // Foreign Key Mapping (Many Attachments -> One Task)
    @NotNull(message = "Task must be associated with attachment")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Attachment() {
    }

    public Attachment(Integer attachmentId, String fileName, String filePath, Task task) {
        this.attachmentId = attachmentId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.task = task;
    }
}
