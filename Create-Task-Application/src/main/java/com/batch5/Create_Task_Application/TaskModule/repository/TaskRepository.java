package com.batch5.Create_Task_Application.TaskModule.repository;

import com.batch5.Create_Task_Application.TaskModule.entity.Category;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByProject_ProjectId(Integer projectId);

    List<Task> findByUser_UserId(Integer userId);

    Optional<Task> findByTaskId(Integer taskId);

    List<Task> findAll();

    //void deleteById(Integer taskId);

    List<Task> findByStatus(String status);

    List<Task> findByPriority(String priority);

}
