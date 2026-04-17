package com.batch5.Create_Task_Application.TaskModule.service;

import com.batch5.Create_Task_Application.TaskModule.dto.TaskDTO;
import com.batch5.Create_Task_Application.TaskModule.entity.Category;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import com.batch5.Create_Task_Application.TaskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.TaskModule.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImple implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task saveTask(Task task){

        return taskRepository.save(task);
    }

    @Override
    public Task getTask(Integer taskId){
        return taskRepository.findByTaskId(taskId)
                .orElseThrow(()-> new TaskNotFoundException("Task assigned is " +
                        "not found"+" "+taskId));
    }
    @Override
    public List<Task> getAllTask(){
        return taskRepository.findAll();
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
    public Task updateTask(Integer taskId, TaskDTO dto){
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
        return taskRepository.save(task);
    }
}
