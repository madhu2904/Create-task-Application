package com.batch5.Create_Task_Application.taskModule.dto;

import java.time.LocalDateTime;
import java.util.List;


public class TaskRequestDTO {
    private String taskName;
    private String description;
    private String priority;
    private String status;
    private LocalDateTime dueDate;
    private Integer projectId;
    private Long userId;

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    private List<Integer> categoryIds;

    public TaskRequestDTO() {
    }
    public TaskRequestDTO(String taskName, String description, String priority, String status, LocalDateTime dueDate, Integer projectId, Long userId,List<Integer> categoryIds) {
        this.taskName = taskName;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.projectId = projectId;
        this.userId = userId;
        this.categoryIds=categoryIds;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
