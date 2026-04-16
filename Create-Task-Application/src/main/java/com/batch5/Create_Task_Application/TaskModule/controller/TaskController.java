package com.batch5.Create_Task_Application.TaskModule.controller;

import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import com.batch5.Create_Task_Application.TaskModule.service.TaskServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")

public class TaskController {
    @Autowired
    TaskServiceImple taskService;

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskService.saveTask(task);
    }

    @GetMapping("/{taskId}")
    public Task displayTask(@PathVariable Integer taskId){
        return taskService.getTask(taskId);
    }

}
