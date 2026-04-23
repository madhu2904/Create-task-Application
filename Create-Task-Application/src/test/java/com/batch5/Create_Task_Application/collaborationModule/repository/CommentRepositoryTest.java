package com.batch5.Create_Task_Application.collaborationModule.repository;

import com.batch5.Create_Task_Application.collaborationModule.entity.Comment;
import com.batch5.Create_Task_Application.projectModule.entity.Project;
import com.batch5.Create_Task_Application.taskModule.entity.Category;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.userModule.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    private Task savedTask;
    private User savedUser;
    private Category savedCategory;
    private Project savedProject;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass123");
        user.setEmail("test@example.com");
        user.setFullName("Test User");
        savedUser = entityManager.persist(user);

        Project project = new Project();
        project.setProjectName("Test Project");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(30));
        project.setUser(savedUser);
        savedProject = entityManager.persist(project);

        Category category = new Category();
        category.setCategoryName("General");
        savedCategory = entityManager.persist(category);

        Set<Category> categories = new HashSet<>();
        categories.add(savedCategory);

        Task task = new Task();
        task.setTaskName("Test Task");
        task.setDescription("A valid description");
        task.setDueDate(LocalDateTime.now().plusDays(5));
        task.setPriority("HIGH");
        task.setStatus("TODO");
        task.setProject(savedProject);
        task.setUser(savedUser);
        task.setCategories(categories);
        savedTask = entityManager.persistAndFlush(task);
    }

    // helper to build a comment with all required fields
    private Comment buildComment(String text, LocalDateTime createdAt, Task task) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setCreatedAt(createdAt);
        comment.setTask(task);
        comment.setUser(savedUser);   // @NotNull — always required
        return comment;
    }

    // helper to build a valid second task
    private Task buildOtherTask() {
        Set<Category> categories = new HashSet<>();
        categories.add(savedCategory);

        Task other = new Task();
        other.setTaskName("Other Task");
        other.setDescription("Another valid description");
        other.setDueDate(LocalDateTime.now().plusDays(3));
        other.setPriority("LOW");
        other.setStatus("TODO");
        other.setProject(savedProject);
        other.setUser(savedUser);       // @NotNull — always required
        other.setCategories(categories);
        return other;
    }

    // Test 1: Comments returned for a valid task
    @Test
    void findCommentsByTaskId_ShouldReturnComments_WhenTaskHasComments() {
        entityManager.persistAndFlush(
                buildComment("First comment", LocalDateTime.now(), savedTask));

        List<Comment> result = commentRepository.findCommentsByTaskId_OrderByDate(savedTask.getTaskId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getText()).isEqualTo("First comment");
    }

    // Test 2: Comments returned in descending order of createdAt
    @Test
    void findCommentsByTaskId_ShouldReturnCommentsInDescendingOrder() {
        entityManager.persist(
                buildComment("Older comment", LocalDateTime.now().minusHours(2), savedTask));
        entityManager.persistAndFlush(
                buildComment("Newer comment", LocalDateTime.now(), savedTask));

        List<Comment> result = commentRepository.findCommentsByTaskId_OrderByDate(savedTask.getTaskId());

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getText()).isEqualTo("Newer comment");
        assertThat(result.get(1).getText()).isEqualTo("Older comment");
    }

    // Test 3: Comments don't bleed across tasks
    @Test
    void findCommentsByTaskId_ShouldOnlyReturnCommentsForSpecificTask() {
        Task savedOtherTask = entityManager.persistAndFlush(buildOtherTask());

        entityManager.persist(
                buildComment("Comment on my task", LocalDateTime.now(), savedTask));
        entityManager.persistAndFlush(
                buildComment("Comment on other task", LocalDateTime.now(), savedOtherTask));

        List<Comment> result = commentRepository.findCommentsByTaskId_OrderByDate(savedTask.getTaskId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getText()).isEqualTo("Comment on my task");
    }

    // Test 4 (Negative): Non-existent task ID returns empty list
    @Test
    void findCommentsByTaskId_ShouldReturnEmptyList_WhenTaskHasNoComments() {
        List<Comment> result = commentRepository.findCommentsByTaskId_OrderByDate(99999);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }
}