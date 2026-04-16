package com.batch5.Create_Task_Application.CollaborationModule.service;

import com.batch5.Create_Task_Application.CollaborationModule.dto.CommentRequestDTO;
import com.batch5.Create_Task_Application.CollaborationModule.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    CommentResponseDTO addComment(Integer taskId, CommentRequestDTO dto);
    List<CommentResponseDTO> getTaskComments(Integer taskId);
    void deleteComment(Integer commentId);
}
