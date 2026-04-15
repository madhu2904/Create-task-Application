package com.batch5.Create_Task_Application.TaskModule.repository;

import com.batch5.Create_Task_Application.TaskModule.entity.Category;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,String> {
    List<Task> findByProjectId(Long projectId);

    List<Task> findByUserId(Long userId);
}
