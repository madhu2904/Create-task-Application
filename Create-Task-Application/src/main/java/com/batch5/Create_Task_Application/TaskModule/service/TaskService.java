package com.batch5.Create_Task_Application.TaskModule.service;

import com.batch5.Create_Task_Application.TaskModule.dto.CategoryRequestDTO;
import com.batch5.Create_Task_Application.TaskModule.dto.CategoryResponseDTO;
import com.batch5.Create_Task_Application.TaskModule.dto.TaskRequestDTO;
import com.batch5.Create_Task_Application.TaskModule.dto.TaskResponseDTO;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;

import java.util.List;

public interface TaskService {
    Task saveTask(TaskRequestDTO task);

    TaskResponseDTO getTask(Integer taskId);

    List<TaskResponseDTO> getAllTask();

    void deleteTask(Integer taskId);

    TaskResponseDTO updateTask(Integer taskId, TaskRequestDTO dto);

    List<TaskResponseDTO> getTaskByProjectId(Integer projectId);

    List<TaskResponseDTO> getTaskByUserId(Integer userId);

    List<TaskResponseDTO> getTaskByStatus(String status);

    List<TaskResponseDTO> getTaskByPriority(String priority);

    void addCategoryToTask(Integer taskId, Integer categoryId);

    void deleteCategoryFromTask(Integer taskId, Integer categoryId);

    List<CategoryResponseDTO> getCategoryFromTask(Integer taskId);
}
