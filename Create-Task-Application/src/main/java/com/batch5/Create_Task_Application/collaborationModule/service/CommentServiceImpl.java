package com.batch5.Create_Task_Application.collaborationModule.service;

import com.batch5.Create_Task_Application.collaborationModule.dto.CommentRequestDTO;
import com.batch5.Create_Task_Application.collaborationModule.dto.CommentResponseDTO;
import com.batch5.Create_Task_Application.collaborationModule.entity.Comment;
import com.batch5.Create_Task_Application.collaborationModule.repository.CommentRepository;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.taskModule.repository.TaskRepository;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.exceptions.UserNotFoundException;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batch5.Create_Task_Application.collaborationModule.exceptions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentResponseDTO addComment(Integer taskId, CommentRequestDTO dto) {

        //  Validate input
        if (dto.getText() == null || dto.getText().trim().isEmpty()) {
            throw new CommentNotFoundException("Comment text cannot be empty");
        }

        if (dto.getUserId() == null) {
            throw new CommentNotFoundException("User ID is required");
        }

        //  Check Task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        //  Check User
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + dto.getUserId()));

        //  Create Comment
        Comment comment = Comment.builder()
                .text(dto.getText())
                .createdAt(LocalDateTime.now())
                .task(task)
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return mapToDTO(savedComment);
    }

    @Override
    public List<CommentResponseDTO> getTaskComments(Integer taskId) {

        // Ensure task exists (IMPORTANT FIX)
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("Task not found with id: " + taskId);
        }

        List<Comment> comments = commentRepository.findCommentsByTaskIdOrderByDate(taskId);

        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Integer commentId) {

        // Fetch instead of exists (cleaner + reusable)
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + commentId));

        commentRepository.delete(comment);
    }

    // Mapping
    private CommentResponseDTO mapToDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .commentId(comment.getCommentId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .taskId(comment.getTask().getTaskId())
                .userId(comment.getUser().getUserId())
                .build();
    }
}