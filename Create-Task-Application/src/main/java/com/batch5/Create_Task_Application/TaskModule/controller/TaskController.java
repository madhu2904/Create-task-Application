package com.batch5.Create_Task_Application.TaskModule.controller;

import com.batch5.Create_Task_Application.TaskModule.Mapper.TaskMapper;
import com.batch5.Create_Task_Application.TaskModule.dto.*;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import com.batch5.Create_Task_Application.TaskModule.service.TaskServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")

public class TaskController {
    @Autowired
    TaskServiceImple taskService;

    @PostMapping
    public ResponseStructureDTO<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO task){
        Task savedTask = taskService.saveTask(task);
        TaskResponseDTO response = TaskMapper.toDTO(savedTask);
        ResponseStructureDTO<TaskResponseDTO> r = new ResponseStructureDTO<>(
                200,
                "Task saved successfully",
                response,
                LocalDateTime.now()
        );
        return r;
    }

    @GetMapping("/{taskId}")
    public ResponseStructureDTO<TaskResponseDTO> displayTask(@PathVariable Integer taskId){
        TaskResponseDTO task = taskService.getTask(taskId);
        ResponseStructureDTO<TaskResponseDTO> r = new ResponseStructureDTO<>(
                200,
                "Task Displayed",
                task,
                LocalDateTime.now()
        );

        return r;
    }

    @GetMapping()
    public ResponseStructureDTO<List<TaskResponseDTO>> getAll(){
        List<TaskResponseDTO> tasks = taskService.getAllTask();
        return new ResponseStructureDTO<>(
                200,
                "All tasks fetched successfully",
                tasks,
                LocalDateTime.now()
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseStructureDTO<String> deleteTasks(@PathVariable Integer taskId){
        taskService.deleteTask(taskId);
        return new ResponseStructureDTO<>(
                200,
                "Task deleted successfully",
                "Deleted task with ID: " + taskId,
                LocalDateTime.now()
        );
    }
    @PutMapping("/{taskId}")
    public ResponseStructureDTO<TaskResponseDTO> updateTasks(@PathVariable Integer taskId,
            @RequestBody TaskRequestDTO dto){
        TaskResponseDTO updatedTask = taskService.updateTask(taskId, dto);
        return new ResponseStructureDTO<>(
                200,
                "Task updated successfully",
                updatedTask,
                LocalDateTime.now()
        );
    }
    @GetMapping("/projects/{projectId}")
    public ResponseStructureDTO<List<TaskResponseDTO>> getTasksByProjectId(
            @PathVariable Integer projectId){
        List<TaskResponseDTO> tasks = taskService.getTaskByProjectId(projectId);
        return new ResponseStructureDTO<>(
                200,
                "Tasks fetched by project",
                tasks,
                LocalDateTime.now()
        );
    }
    @GetMapping("/user/{userId}")
    public ResponseStructureDTO<List<TaskResponseDTO>> getTasksByUserId(
            @PathVariable Integer userId){
        List<TaskResponseDTO> tasks = taskService.getTaskByUserId(userId);

        return new ResponseStructureDTO<>(
                200,
                "Tasks fetched by user",
                tasks,
                LocalDateTime.now()
        );
    }
    @GetMapping("/status/{status}")
    public ResponseStructureDTO<List<TaskResponseDTO>> getTasksByStatus(
            @PathVariable String status){
        List<TaskResponseDTO> tasks = taskService.getTaskByStatus(status);
        return new ResponseStructureDTO<>(
                200,
                "Tasks filtered by status",
                tasks,
                LocalDateTime.now()
        );
    }
    @GetMapping("/priority/{priority}")
    public ResponseStructureDTO<List<TaskResponseDTO>> getTasksByPriority(
            @PathVariable String priority){
        List<TaskResponseDTO> tasks = taskService.getTaskByPriority(priority);
        return new ResponseStructureDTO<>(
                200,
                "Tasks filtered by priority",
                tasks,
                LocalDateTime.now()
        );
    }
    @GetMapping("/{taskId}/categories")
    public ResponseStructureDTO<List<CategoryResponseDTO>> getCategoryFromTask(
            @PathVariable Integer taskId){
        List<CategoryResponseDTO> categories = taskService.getCategoryFromTask(taskId);
        return new ResponseStructureDTO<>(
                200,
                "Categories fetched for task",
                categories,
                LocalDateTime.now()
        );
    }
    @PostMapping("/{taskId}/categories/{categoryId}")
    public ResponseStructureDTO<String> addCategoryToTask(
            @PathVariable Integer taskId,
            @PathVariable Integer categoryId){

        taskService.addCategoryToTask(taskId, categoryId);

        return new ResponseStructureDTO<>(
                200,
                "Category added to task",
                "Task ID: " + taskId + ", Category ID: " + categoryId,
                LocalDateTime.now()
        );
    }
    @DeleteMapping("/{taskId}/categories/{categoryId}")
    public ResponseStructureDTO<String> deleteCategoryFromTask(
            @PathVariable Integer taskId,
            @PathVariable Integer categoryId){
        taskService.deleteCategoryFromTask(taskId, categoryId);
        return new ResponseStructureDTO<>(
                200,
                "Category removed from task",
                "Task ID: " + taskId + ", Category ID: " + categoryId,
                LocalDateTime.now()
        );
    }

}
