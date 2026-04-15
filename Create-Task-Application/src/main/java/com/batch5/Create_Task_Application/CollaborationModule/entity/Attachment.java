package com.batch5.Create_Task_Application.CollaborationModule.entity;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
