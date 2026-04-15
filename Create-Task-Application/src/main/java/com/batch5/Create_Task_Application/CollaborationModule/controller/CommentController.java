package com.batch5.Create_Task_Application.CollaborationModule.controller;

import com.batch5.Create_Task_Application.CollaborationModule.dto.CommentRequestDTO;
import com.batch5.Create_Task_Application.CollaborationModule.dto.CommentResponseDTO;
import com.batch5.Create_Task_Application.CollaborationModule.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Integer taskId,
            @Valid @RequestBody CommentRequestDTO dto) {
        
        CommentResponseDTO createdComment = commentService.addComment(taskId, dto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getTaskComments(@PathVariable Integer taskId) {
        List<CommentResponseDTO> comments = commentService.getTaskComments(taskId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
