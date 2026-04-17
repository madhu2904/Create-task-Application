package com.batch5.Create_Task_Application.taskModule.service;

import com.batch5.Create_Task_Application.taskModule.Mapper.CategoryMapper;
import com.batch5.Create_Task_Application.taskModule.Mapper.TaskMapper;
import com.batch5.Create_Task_Application.taskModule.dto.CategoryResponseDTO;
import com.batch5.Create_Task_Application.taskModule.dto.TaskRequestDTO;
import com.batch5.Create_Task_Application.taskModule.dto.TaskResponseDTO;
import com.batch5.Create_Task_Application.taskModule.entity.Category;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.exceptions.CategoryNotFoundException;
import com.batch5.Create_Task_Application.taskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.taskModule.repository.CategoryRepository;
import com.batch5.Create_Task_Application.taskModule.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImple implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Task saveTask(TaskRequestDTO dto){
        Task task = new Task();
        task.setTaskName(dto.getTaskName());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());
        task.setDueDate(dto.getDueDate());
        return taskRepository.save(task);
    }

    @Override
    public TaskResponseDTO getTask(Integer taskId){
        Task task = taskRepository.findByTaskId(taskId)
                .orElseThrow(()-> new TaskNotFoundException("Task assigned is " +
                        "not found"+" "+taskId));
        return TaskMapper.toDTO(task);
    }
    @Override
    public List<TaskResponseDTO> getAllTask(){
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponseDTO> dtoList = new ArrayList<>();
        for(Task task : tasks){
            dtoList.add(TaskMapper.toDTO(task));
        }
        return dtoList;
    }

    @Override
    public void deleteTask(Integer taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->new TaskNotFoundException("Task not found"+taskId));
        Set<Category> categories = new HashSet<>(task.getCategories());
        for (Category category : categories) {
            category.getTasks().remove(task);
        }
        task.getCategories().clear();
        taskRepository.delete(task);
    }

    @Override
    public TaskResponseDTO updateTask(Integer taskId, TaskRequestDTO dto){
        Task task = taskRepository.findById(taskId).orElseThrow(()->new TaskNotFoundException("Task not found"+taskId));
        Set<Category> categories = new HashSet<>(task.getCategories());
        if(dto.getTaskName()!=null){
            task.setTaskName(dto.getTaskName());
        }
        if(dto.getDescription()!=null){
            task.setDescription(dto.getDescription());
        }
        if(dto.getPriority()!=null){
            task.setPriority(dto.getPriority());
        }
        if(dto.getDueDate()!=null){
            task.setDueDate(dto.getDueDate());
        }
        if(dto.getStatus()!=null){
            task.setStatus(dto.getStatus());
        }
        Task t = taskRepository.save(task);
        return TaskMapper.toDTO(t);
    }
    @Override
    public List<TaskResponseDTO> getTaskByProjectId(Integer projectId){
        List<Task> task = taskRepository.findByProject_ProjectId(projectId);
        return TaskMapper.toDTOList(task);
    }
    @Override
    public List<TaskResponseDTO> getTaskByUserId(Integer userId){
        List<Task> task = taskRepository.findByUser_UserId(userId);
        return TaskMapper.toDTOList(task);
    }
    @Override
    public List<TaskResponseDTO> getTaskByStatus(String status){
        List<Task> task = taskRepository.findByStatus(status);
        List<TaskResponseDTO> dto = new ArrayList<TaskResponseDTO>();
        for(Task t : task){
            dto.add(TaskMapper.toDTO(t));
        }
        return dto;
    }
    @Override
    public List<TaskResponseDTO> getTaskByPriority(String priority){
        List<Task> task = taskRepository.findByPriority(priority);
        List<TaskResponseDTO> dto = new ArrayList<TaskResponseDTO>();
        for(Task t : task){
            dto.add(TaskMapper.toDTO(t));
        }
        return dto;
    }
    @Override
    public void addCategoryToTask(Integer taskId, Integer categoryId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new TaskNotFoundException("Task not Found "+taskId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new CategoryNotFoundException("Category Not Found "+categoryId));
        task.getCategories().add(category);
        taskRepository.save(task);
    }
    @Override
    public void deleteCategoryFromTask(Integer taskId, Integer categoryId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new TaskNotFoundException("Task not Found "+taskId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new CategoryNotFoundException("Category Not Found "+categoryId));
        task.getCategories().remove(category);
        taskRepository.save(task);
    }
    @Override
    public List<CategoryResponseDTO> getCategoryFromTask(Integer taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new TaskNotFoundException("Task not Found "+taskId));
        Set<Category> categories = task.getCategories();
        List<CategoryResponseDTO> dto = new ArrayList<CategoryResponseDTO>();
        for(Category c : categories){
            dto.add(CategoryMapper.toDTO(c));
        }
        return dto;
    }
}
