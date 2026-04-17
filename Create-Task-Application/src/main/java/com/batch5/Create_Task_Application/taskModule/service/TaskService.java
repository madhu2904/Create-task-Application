package com.batch5.Create_Task_Application.taskModule.service;

import com.batch5.Create_Task_Application.taskModule.dto.TaskDTO;
import com.batch5.Create_Task_Application.taskModule.entity.Task;

import java.util.List;

public interface TaskService {
    Task saveTask(Task task);
    Task getTask(Integer taskId);
    List<Task> getAllTask();
    void deleteTask(Integer taskId);
    Task updateTask(Integer taskId, TaskDTO dto);
}
