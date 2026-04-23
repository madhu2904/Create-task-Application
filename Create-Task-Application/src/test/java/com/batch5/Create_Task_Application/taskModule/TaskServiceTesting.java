package com.batch5.Create_Task_Application.taskModule;

import com.batch5.Create_Task_Application.projectModule.entity.Project;
import com.batch5.Create_Task_Application.projectModule.exceptions.ProjectNotFoundException;
import com.batch5.Create_Task_Application.projectModule.repository.ProjectRepository;
import com.batch5.Create_Task_Application.taskModule.dto.TaskRequestDTO;
import com.batch5.Create_Task_Application.taskModule.dto.TaskResponseDTO;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.exceptions.InvalidTaskException;
import com.batch5.Create_Task_Application.taskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.taskModule.repository.CategoryRepository;
import com.batch5.Create_Task_Application.taskModule.repository.TaskRepository;
import com.batch5.Create_Task_Application.taskModule.service.TaskServiceImple;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.exceptions.UserNotFoundException;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskServiceImple taskService;

    @Mock private TaskRepository taskRepository;
    @Mock private UserRepository userRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private CategoryRepository categoryRepository;

    private Task task;
    private User user;
    private Project project;

    @BeforeEach
    void setup() {
        task = new Task();
        task.setCategories(new HashSet<>());
        user = new User();
        project = new Project();
    }

    // ===== POSITIVE =====

    @Test
    void saveTask_success() {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTaskName("Task");
        dto.setUserId(1L);
        dto.setProjectId(1); // ✅ FIXED

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.findById(1)).thenReturn(Optional.of(project)); // ✅ FIXED
        when(taskRepository.save(any())).thenReturn(task);

        assertNotNull(taskService.saveTask(dto));
    }

    @Test
    void getTask_success() {
        when(taskRepository.findByTaskId(1)).thenReturn(Optional.of(task));
        assertNotNull(taskService.getTask(1));
    }

    @Test
    void getAllTask_success() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        assertFalse(taskService.getAllTask().isEmpty());
    }

    @Test
    void updateTask_success() {

        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTaskName("Updated Task");
        dto.setDescription("Updated Desc");
        dto.setPriority("HIGH");
        dto.setStatus("TODO");

        // 👇 VERY IMPORTANT (if your service checks dueDate too)
        dto.setDueDate(LocalDateTime.now().plusDays(1));

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO result = taskService.updateTask(1, dto);

        assertNotNull(result);
    }

    @Test
    void deleteTask_success() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        assertDoesNotThrow(() -> taskService.deleteTask(1));
    }

    // ===== NEGATIVE =====

    @Test
    void saveTask_invalidName() {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTaskName("");

        assertThrows(InvalidTaskException.class,
                () -> taskService.saveTask(dto));
    }

    @Test
    void saveTask_userNotFound() {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTaskName("Task");
        dto.setUserId(1L);
        dto.setProjectId(1); // ✅ FIXED

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> taskService.saveTask(dto));
    }

    @Test
    void saveTask_projectNotFound() {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTaskName("Task");
        dto.setUserId(1L);
        dto.setProjectId(1); // ✅ FIXED

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.findById(1)).thenReturn(Optional.empty()); // ✅ FIXED

        assertThrows(ProjectNotFoundException.class,
                () -> taskService.saveTask(dto));
    }

    @Test
    void getTask_notFound() {
        when(taskRepository.findByTaskId(1)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.getTask(1));
    }

    @Test
    void deleteTask_notFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.deleteTask(1));
    }
}