package com.batch5.Create_Task_Application.CollaborationModule.service;

import com.batch5.Create_Task_Application.CollaborationModule.dto.CommentRequestDTO;
import com.batch5.Create_Task_Application.CollaborationModule.dto.CommentResponseDTO;
import com.batch5.Create_Task_Application.CollaborationModule.entity.Comment;
import com.batch5.Create_Task_Application.CollaborationModule.repository.CommentRepository;
import com.batch5.Create_Task_Application.TaskModule.entity.Task;
import com.batch5.Create_Task_Application.TaskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.TaskModule.repository.TaskRepository;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import com.batch5.Create_Task_Application.UserModule.exceptions.UserNotFoundException;
import com.batch5.Create_Task_Application.UserModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batch5.Create_Task_Application.CollaborationModule.exceptions.CommentNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + dto.getUserId()));

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
        List<Comment> comments = commentRepository.findCommentsByTaskIdOrderByDate(taskId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Integer commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

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
