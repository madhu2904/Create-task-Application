package com.batch5.Create_Task_Application.collaborationModule.service;

import com.batch5.Create_Task_Application.collaborationModule.dto.CommentRequestDTO;
import com.batch5.Create_Task_Application.collaborationModule.dto.CommentResponseDTO;
import com.batch5.Create_Task_Application.collaborationModule.entity.Comment;
import com.batch5.Create_Task_Application.collaborationModule.exceptions.CommentNotFoundException;
import com.batch5.Create_Task_Application.collaborationModule.repository.CommentRepository;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.taskModule.repository.TaskRepository;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Task mockTask;
    private User mockUser;
    private Comment mockComment;

    @BeforeEach
    void setUp() {
        mockTask = new Task();
        mockTask.setTaskId(1);
        mockTask.setTaskName("Test Task");

        mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setUsername("testuser");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("pass123");
        mockUser.setFullName("Test User");

        mockComment = new Comment();
        mockComment.setCommentId(1);
        mockComment.setText("Test comment");
        mockComment.setCreatedAt(LocalDateTime.now());
        mockComment.setTask(mockTask);
        mockComment.setUser(mockUser);
    }

    // Test 1: Add comment successfully
    @Test
    void addComment_ShouldReturnDTO_WhenValidInput() {
        CommentRequestDTO dto = new CommentRequestDTO();
        dto.setText("Test comment");
        dto.setUserId(1L);

        when(taskRepository.findById(1)).thenReturn(Optional.of(mockTask));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);

        CommentResponseDTO result = commentService.addComment(1, dto);

        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo("Test comment");
        assertThat(result.getTaskId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo(1L);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    // Test 2 (Negative): Empty comment text throws exception
    @Test
    void addComment_ShouldThrow_WhenTextIsBlank() {
        CommentRequestDTO dto = new CommentRequestDTO();
        dto.setText("   ");
        dto.setUserId(1L);

        assertThatThrownBy(() -> commentService.addComment(1, dto))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("Comment text cannot be empty");

        verify(commentRepository, never()).save(any());
    }

    // Test 3 (Negative): Null userId throws exception
    @Test
    void addComment_ShouldThrow_WhenUserIdIsNull() {
        CommentRequestDTO dto = new CommentRequestDTO();
        dto.setText("Valid text");
        dto.setUserId(null);

        assertThatThrownBy(() -> commentService.addComment(1, dto))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("User ID is required");

        verify(commentRepository, never()).save(any());
    }

    // Test 4 (Negative): Task not found throws TaskNotFoundException
    @Test
    void addComment_ShouldThrowTaskNotFoundException_WhenTaskDoesNotExist() {
        CommentRequestDTO dto = new CommentRequestDTO();
        dto.setText("Valid text");
        dto.setUserId(1L);

        when(taskRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.addComment(99, dto))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task not found with id: 99");

        verify(commentRepository, never()).save(any());
    }

    // Test 5 (Negative): User not found throws UserNotFoundException
    @Test
    void addComment_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        CommentRequestDTO dto = new CommentRequestDTO();
        dto.setText("Valid text");
        dto.setUserId(99L);

        when(taskRepository.findById(1)).thenReturn(Optional.of(mockTask));
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.addComment(1, dto))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with id: 99");

        verify(commentRepository, never()).save(any());
    }

    // Test 6: Get comments for valid task
    @Test
    void getTaskComments_ShouldReturnList_WhenTaskExistsWithComments() {
        when(taskRepository.existsById(1)).thenReturn(true);
        when(commentRepository.findCommentsByTaskId_OrderByDate(1))
                .thenReturn(List.of(mockComment));

        List<CommentResponseDTO> result = commentService.getTaskComments(1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getText()).isEqualTo("Test comment");
    }

    // Test 7 (Negative): Get comments throws when task not found
    @Test
    void getTaskComments_ShouldThrowTaskNotFoundException_WhenTaskDoesNotExist() {
        when(taskRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> commentService.getTaskComments(99))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task not found with id: 99");

        verify(commentRepository, never()).findCommentsByTaskId_OrderByDate(any());
    }

    // Test 8: Delete comment successfully
    @Test
    void deleteComment_ShouldDelete_WhenCommentExists() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(mockComment));

        commentService.deleteComment(1);

        verify(commentRepository, times(1)).delete(mockComment);
    }

    // Test 9 (Negative): Delete throws when comment not found
    @Test
    void deleteComment_ShouldThrowCommentNotFoundException_WhenNotFound() {
        when(commentRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.deleteComment(99))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("Comment not found with id: 99");

        verify(commentRepository, never()).delete(any());
    }
}