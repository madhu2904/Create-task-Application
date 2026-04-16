package com.batch5.Create_Task_Application.TaskModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String taskName;
    private String description;
    private String priority;
    private String status;
    private LocalDateTime dueDate;

    private Integer projectId;
    private Integer userId;
}
