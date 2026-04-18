package com.batch5.Create_Task_Application.taskModule.controller;


import com.batch5.Create_Task_Application.commonModule.ApiResponse;
import com.batch5.Create_Task_Application.taskModule.mapper.TaskMapper;
import com.batch5.Create_Task_Application.taskModule.dto.*;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.service.TaskServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")

public class TaskController {
    @Autowired
    TaskServiceImple taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponseDTO>> createTask(@RequestBody TaskRequestDTO task){
        Task savedTask = taskService.saveTask(task);
        TaskResponseDTO response = TaskMapper.toDTO(savedTask);
        ApiResponse<TaskResponseDTO> r = new ApiResponse<>(
                200,
                "Task saved successfully",
                response
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> displayTask(@PathVariable Integer taskId){
        TaskResponseDTO task = taskService.getTask(taskId);
        ApiResponse<TaskResponseDTO> r = new ApiResponse<>(
                200,
                "Task Displayed",
                task
        );

        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getAll(){
        List<TaskResponseDTO> tasks = taskService.getAllTask();
        ApiResponse<List<TaskResponseDTO>> r = new ApiResponse<>(
                200,
                "All tasks fetched successfully",
                tasks
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<String>> deleteTasks(@PathVariable Integer taskId){
        taskService.deleteTask(taskId);
        ApiResponse<String> r = new ApiResponse<>(
                200,
                "Task deleted successfully",
                "Deleted task with ID: " + taskId
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> updateTasks(@PathVariable Integer taskId,
                                                    @RequestBody TaskRequestDTO dto){
        TaskResponseDTO updatedTask = taskService.updateTask(taskId, dto);
        ApiResponse<TaskResponseDTO> r = new ApiResponse<>(
                200,
                "Task updated successfully",
                updatedTask
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getTasksByProjectId(
            @PathVariable Integer projectId){
        List<TaskResponseDTO> tasks = taskService.getTaskByProjectId(projectId);
        ApiResponse<List<TaskResponseDTO>> r = new ApiResponse<>(
                200,
                "Tasks fetched by project",
                tasks
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getTasksByUserId(
            @PathVariable Long userId){
        List<TaskResponseDTO> tasks = taskService.getTaskByUserId(userId);

        ApiResponse<List<TaskResponseDTO>> r = new ApiResponse<>(
                200,
                "Tasks fetched by user",
                tasks
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getTasksByStatus(
            @PathVariable String status){
        List<TaskResponseDTO> tasks = taskService.getTaskByStatus(status);
        ApiResponse<List<TaskResponseDTO>> r = new ApiResponse<>(
                200,
                "Tasks filtered by status",
                tasks
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
    @GetMapping("/priority/{priority}")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getTasksByPriority(
            @PathVariable String priority){
        List<TaskResponseDTO> tasks = taskService.getTaskByPriority(priority);
        ApiResponse<List<TaskResponseDTO>> r = new ApiResponse<>(
                200,
                "Tasks filtered by priority",
                tasks
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
    @GetMapping("/{taskId}/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getCategoryFromTask(
            @PathVariable Integer taskId){
        List<CategoryResponseDTO> categories = taskService.getCategoryFromTask(taskId);
        ApiResponse<List<CategoryResponseDTO>> r = new ApiResponse<>(
                200,
                "Categories fetched for task",
                categories
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
    @PostMapping("/{taskId}/categories/{categoryId}")
    public ResponseEntity<ApiResponse<String>> addCategoryToTask(
            @PathVariable Integer taskId,
            @PathVariable Integer categoryId){

        taskService.addCategoryToTask(taskId, categoryId);

        ApiResponse<String> r = new ApiResponse<>(
                200,
                "Category added to task",
                "Task ID: " + taskId + ", Category ID: " + categoryId
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
    @DeleteMapping("/{taskId}/categories/{categoryId}")
    public ResponseEntity<ApiResponse<String>> deleteCategoryFromTask(
            @PathVariable Integer taskId,
            @PathVariable Integer categoryId){
        taskService.deleteCategoryFromTask(taskId, categoryId);
        ApiResponse<String> r = new ApiResponse<>(
                200,
                "Category removed from task",
                "Task ID: " + taskId + ", Category ID: " + categoryId
        );
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

}
