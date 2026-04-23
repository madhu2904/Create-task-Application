package com.batch5.Create_Task_Application.taskModule.repository;

import com.batch5.Create_Task_Application.taskModule.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByProject_ProjectId(Integer projectId);

    List<Task> findByUser_UserId(Long userId);

    Optional<Task> findByTaskId(Integer taskId);

    List<Task> findAll();

    //void deleteById(Integer taskId);

    List<Task> findByStatus(String status);

    List<Task> findByPriority(String priority);

}
