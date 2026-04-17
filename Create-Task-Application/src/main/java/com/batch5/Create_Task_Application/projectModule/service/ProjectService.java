package com.batch5.Create_Task_Application.projectModule.service;

import com.batch5.Create_Task_Application.projectModule.dto.ProjectRequestDto;
import com.batch5.Create_Task_Application.projectModule.dto.ProjectResponseDto;

import java.util.List;

public interface ProjectService {

    ProjectResponseDto createProject(ProjectRequestDto requestDto);

    ProjectResponseDto getProjectById(Integer projectId);

    List<ProjectResponseDto> getAllProjects();

    List<ProjectResponseDto> getProjectsByUser(Long userId);

    ProjectResponseDto updateProject(Integer projectId, ProjectRequestDto requestDto);

    void deleteProject(Integer projectId);

    List<ProjectResponseDto> getActiveProjects();

    List<ProjectResponseDto> searchProjectsByName(String keyword);
}