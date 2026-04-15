package com.batch5.Create_Task_Application.ProjectModule.controller;

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
    public ResponseEntity<ProjectResponseDto> createProject(
            @Valid @RequestBody ProjectRequestDto requestDto) {

        ProjectResponseDto response = projectService.createProject(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/projects/{projectId}
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> getProjectById(
            @PathVariable Integer projectId) {

        ProjectResponseDto response = projectService.getProjectById(projectId);
        return ResponseEntity.ok(response);
    }

    // GET /api/projects
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {

        List<ProjectResponseDto> response = projectService.getAllProjects();
        return ResponseEntity.ok(response);
    }

    // GET /api/projects/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByUser(
            @PathVariable Integer userId) {

        List<ProjectResponseDto> response = projectService.getProjectsByUser(userId);
        return ResponseEntity.ok(response);
    }

    // PUT /api/projects/{projectId}
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> updateProject(
            @PathVariable Integer projectId,
            @Valid @RequestBody ProjectRequestDto requestDto) {

        ProjectResponseDto response = projectService.updateProject(projectId, requestDto);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/projects/{projectId}
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(
            @PathVariable Integer projectId) {

        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted successfully");
    }
}