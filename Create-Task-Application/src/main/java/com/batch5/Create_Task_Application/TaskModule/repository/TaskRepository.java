package com.batch5.Create_Task_Application.TaskModule.repository;

import com.batch5.Create_Task_Application.TaskModule.entity.Category;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByProjectId(Integer projectId);

    List<Task> findByUserId(Long userId);

    Optional<Task> findByTaskId(Integer taskId);

    List<Task> findByStatus(String status);

    List<Task> findByPriority(String priority);
}
