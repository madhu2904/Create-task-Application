package com.batch5.Create_Task_Application.TaskModule.repository;

import com.batch5.Create_Task_Application.TaskModule.entity.Category;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByProject_ProjectId(Integer projectId);

    List<Task> findByUser_UserId(Long userId);
}
