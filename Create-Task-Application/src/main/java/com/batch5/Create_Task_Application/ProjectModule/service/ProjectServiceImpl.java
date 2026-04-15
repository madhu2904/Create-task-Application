package com.batch5.Create_Task_Application.ProjectModule.service;

import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectRequestDto;
import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectResponseDto;
import com.batch5.Create_Task_Application.ProjectModule.entity.Project;
import com.batch5.Create_Task_Application.ProjectModule.repository.ProjectRepository;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import com.batch5.Create_Task_Application.UserModule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto requestDto) {

        // 1. Fetch the owning User — throw if not found
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + requestDto.getUserId()));

        // 2. Build the Project entity
        Project project = new Project();
        project.setProjectName(requestDto.getProjectName());
        project.setDescription(requestDto.getDescription());
        project.setStartDate(requestDto.getStartDate());
        project.setEndDate(requestDto.getEndDate());
        project.setUser(user);

        // 3. Persist
        Project saved = projectRepository.save(project);

        // 4. Map to response DTO
        return mapToResponseDto(saved);
    }

    // ── helper
    private ProjectResponseDto mapToResponseDto(Project project) {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setProjectId(project.getProjectId());
        dto.setProjectName(project.getProjectName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setUserId(project.getUser().getUserId());
        dto.setOwnerName(project.getUser().getFullName()); // adjust getter if named differently
        return dto;
    }
}