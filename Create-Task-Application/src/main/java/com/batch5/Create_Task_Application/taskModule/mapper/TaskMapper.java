package com.batch5.Create_Task_Application.taskModule.mapper;

import com.batch5.Create_Task_Application.taskModule.dto.TaskResponseDTO;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.dto.CategoryResponseDTO;
import com.batch5.Create_Task_Application.taskModule.mapper.CategoryMapper;

import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

public class TaskMapper {
    public static TaskResponseDTO toDTO(Task task) {

        TaskResponseDTO dto = new TaskResponseDTO();

        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getTaskName());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        if(task.getProject() != null){
            dto.setProjectId(task.getProject().getProjectId());
        }
        if(task.getUser() != null){
            dto.setUserId(task.getUser().getUserId());
        }
        if (task.getCategories() != null && !task.getCategories().isEmpty()) {
            dto.setCategories(
                    task.getCategories()
                            .stream()
                            .map(CategoryMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    // Convert LIST of Task → List of TaskDTO
    public static List<TaskResponseDTO> toDTOList(List<Task> tasks) {
        List<TaskResponseDTO> dtoList = new ArrayList<TaskResponseDTO>();
        for (Task task : tasks) {
            TaskResponseDTO dto = toDTO(task);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
