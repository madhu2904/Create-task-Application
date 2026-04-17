package com.batch5.Create_Task_Application.ProjectModule.repository;

import com.batch5.Create_Task_Application.ProjectModule.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // get all projects of a specific user
    List<Project> findByUserUserId(Long userId);

    // search projects by name
    List<Project> findByProjectNameContainingIgnoreCase(String keyword);

    @Query("SELECT p FROM Project p WHERE CURRENT_DATE BETWEEN p.startDate AND p.endDate")
    List<Project> findActiveProjects();
}