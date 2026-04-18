package com.batch5.Create_Task_Application.taskModule.dto;

import lombok.*;

import java.time.LocalDateTime;


public class TaskRequestDTO {
    public TaskRequestDTO() {
    }

    private String taskName;

    public TaskRequestDTO(String taskName, String description, String priority, String status, LocalDateTime dueDate, Integer projectId, Integer userId) {
        this.taskName = taskName;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.projectId = projectId;
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private String description;
    private String priority;
    private String status;
    private LocalDateTime dueDate;
    private Integer projectId;
    private Integer userId;
}
