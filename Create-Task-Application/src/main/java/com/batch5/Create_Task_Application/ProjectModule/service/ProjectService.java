package com.batch5.Create_Task_Application.ProjectModule.service;

import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectRequestDto;
import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectResponseDto;

import java.util.List;

public interface ProjectService {

    ProjectResponseDto createProject(ProjectRequestDto requestDto);

    ProjectResponseDto getProjectById(Integer projectId);

    List<ProjectResponseDto> getAllProjects();

    List<ProjectResponseDto> getProjectsByUser(Integer userId);

    ProjectResponseDto updateProject(Integer projectId, ProjectRequestDto requestDto);

    void deleteProject(Integer projectId);

    List<ProjectResponseDto> getActiveProjects();

    List<ProjectResponseDto> searchProjectsByName(String keyword);
}