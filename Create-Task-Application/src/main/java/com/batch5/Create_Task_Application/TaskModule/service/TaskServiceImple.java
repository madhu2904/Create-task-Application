package com.batch5.Create_Task_Application.TaskModule.service;

import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import com.batch5.Create_Task_Application.TaskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.TaskModule.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
