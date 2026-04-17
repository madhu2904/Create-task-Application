package com.batch5.Create_Task_Application.CollaborationModule.controller;

import com.batch5.Create_Task_Application.CollaborationModule.dto.*;
import com.batch5.Create_Task_Application.CollaborationModule.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // ===================== CREATE =====================
    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CollaborationDTO<CommentResponseDTO>> addComment(
            @PathVariable Integer taskId,
            @Valid @RequestBody CommentRequestDTO dto) {

        CommentResponseDTO created = commentService.addComment(taskId, dto);

        return ResponseEntity.status( HttpStatus.CREATED)
                .body(new CollaborationDTO<>( HttpStatus.CREATED.value(), "Comment added successfully", created));
    }

    // ===================== READ =====================
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CollaborationDTO<List<CommentResponseDTO>>> getTaskComments(
            @PathVariable Integer taskId) {

        List<CommentResponseDTO> comments =
                commentService.getTaskComments(taskId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CollaborationDTO<>(HttpStatus.OK.value(), "Comments fetched successfully", comments));
    }

    //
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CollaborationDTO<Void>> deleteComment(
            @PathVariable Integer commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CollaborationDTO<>(HttpStatus.OK.value(), "Comment deleted successfully", null));
    }
}