package com.batch5.Create_Task_Application.projectModule.service;

import com.batch5.Create_Task_Application.projectModule.dto.ProjectRequestDto;
import com.batch5.Create_Task_Application.projectModule.dto.ProjectResponseDto;
import com.batch5.Create_Task_Application.projectModule.entity.Project;
import com.batch5.Create_Task_Application.projectModule.exceptions.ProjectNotFoundException;
import com.batch5.Create_Task_Application.projectModule.repository.ProjectRepository;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.exceptions.UserNotFoundException;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private User mockUser;
    private Project mockProject;
    private ProjectRequestDto requestDto;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .userId(1L)
                .username("testuser")
                .password("pass123")
                .email("test@gmail.com")
                .fullName("Test User")
                .build();

        mockProject = new Project();
        mockProject.setProjectId(1);
        mockProject.setProjectName("Task Manager App");
        mockProject.setDescription("Spring Boot project");
        mockProject.setStartDate(LocalDate.now().minusDays(3));
        mockProject.setEndDate(LocalDate.now().plusDays(7));
        mockProject.setUser(mockUser);

        requestDto = new ProjectRequestDto();
        requestDto.setProjectName("Task Manager App");
        requestDto.setDescription("Spring Boot project");
        requestDto.setStartDate(LocalDate.now().minusDays(3));
        requestDto.setEndDate(LocalDate.now().plusDays(7));
        requestDto.setUserId(1L);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 1 (VALID): createProject — happy path
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void createProject_ShouldReturnResponseDto_WhenValidRequest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(projectRepository.save(any(Project.class))).thenReturn(mockProject);

        ProjectResponseDto result = projectService.createProject(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getProjectName()).isEqualTo("Task Manager App");
        assertThat(result.getUserName()).isEqualTo("Test User");
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 2 (INVALID): createProject — user not found → throws UserNotFoundException
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void createProject_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.createProject(requestDto))
                .isInstanceOf(UserNotFoundException.class);

        verify(projectRepository, never()).save(any());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 3 (VALID): getProjectById — project exists → returns dto
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void getProjectById_ShouldReturnResponseDto_WhenProjectExists() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));

        ProjectResponseDto result = projectService.getProjectById(1);

        assertThat(result).isNotNull();
        assertThat(result.getProjectId()).isEqualTo(1);
        assertThat(result.getProjectName()).isEqualTo("Task Manager App");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 4 (INVALID): getProjectById — project missing → throws ProjectNotFoundException
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void getProjectById_ShouldThrowProjectNotFoundException_WhenProjectDoesNotExist() {
        when(projectRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.getProjectById(99))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 5 (VALID): getAllProjects — returns mapped list
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void getAllProjects_ShouldReturnListOfProjects() {
        when(projectRepository.findAll()).thenReturn(List.of(mockProject));

        List<ProjectResponseDto> results = projectService.getAllProjects();

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getProjectName()).isEqualTo("Task Manager App");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 6 (VALID): updateProject — valid ids → fields updated
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void updateProject_ShouldUpdateAndReturnResponseDto_WhenValidIds() {
        requestDto.setProjectName("Updated App");

        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> {
            Project saved = inv.getArgument(0);
            saved.setProjectId(1);
            return saved;
        });

        ProjectResponseDto result = projectService.updateProject(1, requestDto);

        assertThat(result.getProjectName()).isEqualTo("Updated App");
        verify(projectRepository).save(any(Project.class));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 7 (VALID): deleteProject — project exists → deleted successfully
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void deleteProject_ShouldDeleteProject_WhenProjectExists() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));
        doNothing().when(projectRepository).delete(mockProject);

        assertThatCode(() -> projectService.deleteProject(1))
                .doesNotThrowAnyException();

        verify(projectRepository).delete(mockProject);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 8 (INVALID): deleteProject — project missing → throws ProjectNotFoundException
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void deleteProject_ShouldThrowProjectNotFoundException_WhenProjectDoesNotExist() {
        when(projectRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.deleteProject(99))
                .isInstanceOf(ProjectNotFoundException.class);

        verify(projectRepository, never()).delete(any());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 9 (INVALID): searchProjectsByName — empty keyword → throws IllegalArgumentException
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    void searchProjectsByName_ShouldThrowIllegalArgumentException_WhenKeywordIsBlank() {
        assertThatThrownBy(() -> projectService.searchProjectsByName("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("keyword must not be empty");

        verify(projectRepository, never()).findByProjectNameContainingIgnoreCase(any());
    }
}