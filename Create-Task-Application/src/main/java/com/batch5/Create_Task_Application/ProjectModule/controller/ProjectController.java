package com.batch5.Create_Task_Application.ProjectModule.controller;

import com.batch5.Create_Task_Application.ProjectModule.dto.ApiResponse;
import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectRequestDto;
import com.batch5.Create_Task_Application.ProjectModule.dto.ProjectResponseDto;
import com.batch5.Create_Task_Application.ProjectModule.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // POST /api/projects/createProject
    @PostMapping("/createProject")
    public ResponseEntity<ApiResponse<ProjectResponseDto>> createProject(
            @Valid @RequestBody ProjectRequestDto requestDto) {

        ProjectResponseDto response = projectService.createProject(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Project created successfully", response));
    }

    // GET /api/projects/{projectId}
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectResponseDto>> getProjectById(
            @PathVariable Integer projectId) {

        ProjectResponseDto response = projectService.getProjectById(projectId);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Project fetched successfully", response));
    }

    // GET /api/projects
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponseDto>>> getAllProjects() {

        List<ProjectResponseDto> response = projectService.getAllProjects();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "All projects fetched successfully", response));
    }

    // GET /api/projects/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ProjectResponseDto>>> getProjectsByUser(
            @PathVariable Long userId) {

        List<ProjectResponseDto> response = projectService.getProjectsByUser(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Projects fetched for user successfully", response));
    }

    // PUT /api/projects/{projectId}
    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectResponseDto>> updateProject(
            @PathVariable Integer projectId,
            @Valid @RequestBody ProjectRequestDto requestDto) {

        ProjectResponseDto response = projectService.updateProject(projectId, requestDto);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Project updated successfully", response));
    }

    // DELETE /api/projects/{projectId}
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse<String>> deleteProject(
            @PathVariable Integer projectId) {

        projectService.deleteProject(projectId);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Project deleted successfully", null));
    }

    // GET /api/projects/search?keyword=xyz
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProjectResponseDto>>> searchProjectsByName(
            @RequestParam String keyword) {

        List<ProjectResponseDto> response = projectService.searchProjectsByName(keyword);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Search results fetched successfully", response));
    }

    // GET /api/projects/active
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ProjectResponseDto>>> getActiveProjects() {

        List<ProjectResponseDto> response = projectService.getActiveProjects();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Active projects fetched successfully", response));
    }
}