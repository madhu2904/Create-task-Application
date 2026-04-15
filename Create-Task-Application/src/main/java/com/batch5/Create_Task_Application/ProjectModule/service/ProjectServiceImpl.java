package com.batch5.Create_Task_Application.ProjectModule.service;

import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectRequestDto;
import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectResponseDto;
import com.batch5.Create_Task_Application.ProjectModule.entity.Project;
import com.batch5.Create_Task_Application.ProjectModule.repository.ProjectRepository;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import com.batch5.Create_Task_Application.UserModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + requestDto.getUserId()));

        Project project = new Project();
        project.setProjectName(requestDto.getProjectName());
        project.setDescription(requestDto.getDescription());
        project.setStartDate(requestDto.getStartDate());
        project.setEndDate(requestDto.getEndDate());
        project.setUser(user);

        Project savedProject = projectRepository.save(project);

        return mapToResponseDto(savedProject);
    }

    @Override
    public ProjectResponseDto getProjectById(Integer projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found with id: " + projectId));

        return mapToResponseDto(project);
    }

    @Override
    public List<ProjectResponseDto> getAllProjects() {

        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDto> getProjectsByUser(Integer userId) {

        // Check whether user exists
        userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + userId));

        List<Project> projects = projectRepository.findByUserUserId(userId);

        return projects.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto updateProject(Integer projectId, ProjectRequestDto requestDto) {

        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found with id: " + projectId));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + requestDto.getUserId()));

        existingProject.setProjectName(requestDto.getProjectName());
        existingProject.setDescription(requestDto.getDescription());
        existingProject.setStartDate(requestDto.getStartDate());
        existingProject.setEndDate(requestDto.getEndDate());
        existingProject.setUser(user);

        Project updatedProject = projectRepository.save(existingProject);

        return mapToResponseDto(updatedProject);
    }

    @Override
    public void deleteProject(Integer projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found with id: " + projectId));

        projectRepository.delete(project);
    }

    private ProjectResponseDto mapToResponseDto(Project project) {

        ProjectResponseDto dto = new ProjectResponseDto();

        dto.setProjectId(project.getProjectId());
        dto.setProjectName(project.getProjectName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setUserId(project.getUser().getUserId());
        dto.setOwnerName(project.getUser().getFullName());

        return dto;
    }
}