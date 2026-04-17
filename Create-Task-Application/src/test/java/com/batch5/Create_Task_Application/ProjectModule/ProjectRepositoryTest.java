package com.batch5.Create_Task_Application.ProjectModule;

import com.batch5.Create_Task_Application.ProjectModule.entity.Project;
import com.batch5.Create_Task_Application.ProjectModule.repository.ProjectRepository;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import com.batch5.Create_Task_Application.UserModule.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Project testProject;

    @BeforeEach
    void setUp() {
        // clean slate before each test
        projectRepository.deleteAll();
        userRepository.deleteAll();

        // save a user first (Project needs a User FK)
        testUser = userRepository.save(User.builder()
                .username("testuser")
                .password("pass123")
                .email("test@gmail.com")
                .fullName("Test User")
                .build());

        // save a project linked to that user
        Project p = new Project();
        p.setProjectName("Task Manager App");
        p.setDescription("Spring Boot project");
        p.setStartDate(LocalDate.now().minusDays(3));  // started 3 days ago
        p.setEndDate(LocalDate.now().plusDays(7));      // ends 7 days later
        p.setUser(testUser);
        testProject = projectRepository.save(p);
    }

    // Test 1: Save and retrieve by ID
    @Test
    void shouldSaveAndFindProjectById() {
        Optional<Project> found = projectRepository.findById(testProject.getProjectId());

        assertThat(found).isPresent();
        assertThat(found.get().getProjectName()).isEqualTo("Task Manager App");
        assertThat(found.get().getUser().getUsername()).isEqualTo("testuser");
    }

    // Test 2: findByUserUserId — get all projects belonging to a user
    @Test
    void shouldFindProjectsByUserId() {
        List<Project> projects = projectRepository.findByUserUserId(testUser.getUserId());

        assertThat(projects).hasSize(1);
        assertThat(projects.get(0).getProjectName()).isEqualTo("Task Manager App");
    }

    // Test 3: findByProjectNameContainingIgnoreCase — keyword search
    @Test
    void shouldFindProjectByNameKeyword() {
        // save one more project to make search meaningful
        Project p2 = new Project();
        p2.setProjectName("Inventory System");
        p2.setStartDate(LocalDate.now());
        p2.setEndDate(LocalDate.now().plusDays(5));
        p2.setUser(testUser);
        projectRepository.save(p2);

        List<Project> results = projectRepository
                .findByProjectNameContainingIgnoreCase("task");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getProjectName()).isEqualTo("Task Manager App");
    }

    // Test 4: findActiveProjects — CURRENT_DATE between startDate and endDate
    @Test
    void shouldFindActiveProjects() {
        // testProject already spans today (setup: -3 days to +7 days)
        List<Project> active = projectRepository.findActiveProjects();

        assertThat(active).isNotEmpty();
        assertThat(active.get(0).getProjectName()).isEqualTo("Task Manager App");
    }

    // Test 5: Update project name and verify
    @Test
    void shouldUpdateProjectName() {
        testProject.setProjectName("Updated Project Name");
        projectRepository.save(testProject);

        Project updated = projectRepository.findById(testProject.getProjectId()).orElseThrow();
        assertThat(updated.getProjectName()).isEqualTo("Updated Project Name");
    }

    // Test 6: Delete project
    @Test
    void shouldDeleteProject() {
        projectRepository.deleteById(testProject.getProjectId());

        Optional<Project> found = projectRepository.findById(testProject.getProjectId());
        assertThat(found).isEmpty();
    }
}