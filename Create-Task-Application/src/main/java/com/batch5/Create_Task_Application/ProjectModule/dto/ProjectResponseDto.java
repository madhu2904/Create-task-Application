package com.batch5.Create_Task_Application.ProjectModule.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectResponseDto {

    private Integer projectId;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
    private String ownerName;  // resolved from User.fullName
}