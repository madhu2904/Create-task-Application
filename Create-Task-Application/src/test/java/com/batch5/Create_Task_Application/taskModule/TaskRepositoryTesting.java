package com.batch5.Create_Task_Application.taskModule;

import com.batch5.Create_Task_Application.projectModule.entity.Project;
import com.batch5.Create_Task_Application.projectModule.repository.ProjectRepository;
import com.batch5.Create_Task_Application.taskModule.entity.Category;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.repository.CategoryRepository;
import com.batch5.Create_Task_Application.taskModule.repository.TaskRepository;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class TaskRepositoryTesting {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // ========== POSITIVE TEST CASES ==========

    @Test
    void testSaveTask() {

        // --- USER ---
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("test@gmail.com");
        user.setFullName("Test User");
        user = userRepository.save(user);

        // --- PROJECT ---
        Project project = new Project();
        project.setProjectName("Test Project");
        project.setDescription("Project Desc");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(5));
        project.setUser(user);
        project = projectRepository.save(project);

        // --- CATEGORY ---
        Category category = new Category();
        category.setCategoryName("Work");
        category = categoryRepository.save(category);

        // --- TASK ---
        Task task = new Task();
        task.setTaskName("Task");
        task.setDescription("Task Description");
        task.setPriority("HIGH");
        task.setStatus("TODO");
        task.setDueDate(LocalDateTime.now().plusDays(1)); // ✅ FIXED

        task.setUser(user);
        task.setProject(project);
        task.setCategories(Set.of(category));

        Task saved = taskRepository.save(task);

        assertNotNull(saved);
    }

    @Test
    @DisplayName("Find Task By ID - Success")
    void testFindById() {

        // --- USER ---
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass123");
        user.setEmail("user@gmail.com");
        user.setFullName("User Test");
        user = userRepository.save(user);

        // --- PROJECT ---
        Project project = new Project();
        project.setProjectName("Project1");
        project.setDescription("Desc");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(5));
        project.setUser(user);
        project = projectRepository.save(project);

        // --- CATEGORY ---
        Category category = new Category();
        category.setCategoryName("Work");
        category = categoryRepository.save(category);

        // --- TASK ---
        Task task = new Task();
        task.setTaskName("Find Task");
        task.setDescription("Test Description");
        task.setPriority("HIGH");
        task.setStatus("TODO");
        task.setDueDate(LocalDateTime.now().plusDays(1)); // ✅ future

        task.setUser(user);
        task.setProject(project);
        task.setCategories(Set.of(category));

        task = taskRepository.save(task);

        // --- TEST ---
        Optional<Task> found = taskRepository.findById(task.getTaskId());

        assertTrue(found.isPresent());
    }

    @Test
    void testFindAll() {

        // --- USER ---
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("test@gmail.com");
        user.setFullName("Test User");
        user = userRepository.save(user);

        // --- PROJECT ---
        Project project = new Project();
        project.setProjectName("Test Project");
        project.setDescription("Project Desc");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(5));
        project.setUser(user);
        project = projectRepository.save(project);

        // --- CATEGORY ---
        Category category = new Category();
        category.setCategoryName("Work");
        category = categoryRepository.save(category);

        // --- TASK ---
        Task task = new Task();
        task.setTaskName("Test Task");
        task.setDescription("Test Description");
        task.setPriority("HIGH");
        task.setStatus("TODO");
        task.setDueDate(LocalDateTime.now().plusDays(1)); // ✅ FIX

        task.setUser(user);
        task.setProject(project);
        task.setCategories(Set.of(category));

        taskRepository.save(task);

        // --- TEST ---
        List<Task> tasks = taskRepository.findAll();

        assertFalse(tasks.isEmpty());
    }

    // ========== NEGATIVE TEST CASES ==========

    @Test
    @DisplayName("Find Task By Invalid ID - Fail")
    void testFindByInvalidId() {
        Optional<Task> task = taskRepository.findById(999);

        assertFalse(task.isPresent());
    }

    @Test
    @DisplayName("Delete Non-existing Task - Fail")
    void testDeleteInvalidTask() {
        assertDoesNotThrow(() -> taskRepository.deleteById(999));
    }
}
