package com.batch5.Create_Task_Application.ProjectModule.service;

import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectRequestDto;
import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectResponseDto;
import com.batch5.Create_Task_Application.ProjectModule.entity.Project;
import com.batch5.Create_Task_Application.ProjectModule.exceptions.ProjectNotFoundException;
import com.batch5.Create_Task_Application.ProjectModule.exceptions.ProjectSearchException;
import com.batch5.Create_Task_Application.ProjectModule.repository.ProjectRepository;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import com.batch5.Create_Task_Application.UserModule.exceptions.UserNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException(requestDto.getUserId()));

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
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        return mapToResponseDto(project);
    }

    @Override
    public List<ProjectResponseDto> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDto> getProjectsByUser(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return projectRepository.findByUserUserId(userId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto updateProject(Integer projectId, ProjectRequestDto requestDto) {

        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(requestDto.getUserId()));

        existingProject.setProjectName(requestDto.getProjectName());
        existingProject.setDescription(requestDto.getDescription());
        existingProject.setStartDate(requestDto.getStartDate());
        existingProject.setEndDate(requestDto.getEndDate());
        existingProject.setUser(user);

        return mapToResponseDto(projectRepository.save(existingProject));
    }

    @Override
    public void deleteProject(Integer projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        projectRepository.delete(project);
    }

    @Override
    public List<ProjectResponseDto> searchProjectsByName(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword must not be empty");
        }

        List<Project> projects = projectRepository
                .findByProjectNameContainingIgnoreCase(keyword.trim());

        if (projects.isEmpty()) {
            throw new ProjectSearchException(keyword);
        }

        return projects.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDto> getActiveProjects() {

        List<Project> projects = projectRepository.findActiveProjects();

        if (projects.isEmpty()) {
            throw new ProjectNotFoundException("No active projects found");
        }

        return projects.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private ProjectResponseDto mapToResponseDto(Project project) {

        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setProjectId(project.getProjectId());
        dto.setProjectName(project.getProjectName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setUserId(project.getUser().getUserId());
        dto.setUserName(project.getUser().getFullName());
        return dto;
    }
}