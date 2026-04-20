package com.batch5.Create_Task_Application.collaborationModule.repository;

import com.batch5.Create_Task_Application.collaborationModule.entity.Attachment;
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
class AttachmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AttachmentRepository attachmentRepository;

    private Task savedTask;
    private Category savedCategory;
    private User savedUser;
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

    // helper to build a valid second task (reused in multiple tests)
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
        other.setUser(savedUser);
        other.setCategories(categories);
        return other;
    }

    // Test 1: Single attachment retrieved by task ID
    @Test
    void findByTask_TaskId_ShouldReturnAttachment_WhenOneExists() {
        Attachment attachment = new Attachment();
        attachment.setFileName("report.pdf");
        attachment.setFilePath("/files/report.pdf");   // .pdf ✓
        attachment.setTask(savedTask);
        entityManager.persistAndFlush(attachment);

        List<Attachment> result = attachmentRepository.findByTask_TaskId(savedTask.getTaskId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFileName()).isEqualTo("report.pdf");
        assertThat(result.get(0).getFilePath()).isEqualTo("/files/report.pdf");
    }

    // Test 2: Multiple attachments for same task — all filePaths match .jpg|.png|.pdf
    @Test
    void findByTask_TaskId_ShouldReturnAll_WhenMultipleAttachmentsExist() {
        Attachment a1 = new Attachment();
        a1.setFileName("design.png");
        a1.setFilePath("/files/design.png");   // .png ✓
        a1.setTask(savedTask);

        Attachment a2 = new Attachment();
        a2.setFileName("summary.pdf");
        a2.setFilePath("/files/summary.pdf");  // .pdf ✓
        a2.setTask(savedTask);

        entityManager.persist(a1);
        entityManager.persistAndFlush(a2);

        List<Attachment> result = attachmentRepository.findByTask_TaskId(savedTask.getTaskId());

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Attachment::getFileName)
                .containsExactlyInAnyOrder("design.png", "summary.pdf");
    }

    // Test 3: Attachments not mixed between tasks
    @Test
    void findByTask_TaskId_ShouldNotReturnAttachmentsOfOtherTasks() {
        Task savedOtherTask = entityManager.persistAndFlush(buildOtherTask());

        Attachment a1 = new Attachment();
        a1.setFileName("mine.pdf");
        a1.setFilePath("/files/mine.pdf");      // .pdf ✓
        a1.setTask(savedTask);

        Attachment a2 = new Attachment();
        a2.setFileName("theirs.png");
        a2.setFilePath("/files/theirs.png");    // .png ✓
        a2.setTask(savedOtherTask);

        entityManager.persist(a1);
        entityManager.persistAndFlush(a2);

        List<Attachment> result = attachmentRepository.findByTask_TaskId(savedTask.getTaskId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFileName()).isEqualTo("mine.pdf");
    }

    // Test 4 (Negative): Non-existent task ID returns empty list
    @Test
    void findByTask_TaskId_ShouldReturnEmptyList_WhenTaskDoesNotExist() {
        List<Attachment> result = attachmentRepository.findByTask_TaskId(99999);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }
}