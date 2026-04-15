package com.batch5.Create_Task_Application.ProjectModule.service;

import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectRequestDto;
import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectResponseDto;

public interface ProjectService {

    ProjectResponseDto createProject(ProjectRequestDto requestDto);

    // add other method signatures here later (getById, update, delete, etc.)
}