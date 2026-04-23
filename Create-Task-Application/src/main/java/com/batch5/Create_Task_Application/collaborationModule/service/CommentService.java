package com.batch5.Create_Task_Application.collaborationModule.service;

import com.batch5.Create_Task_Application.collaborationModule.dto.CommentRequestDTO;
import com.batch5.Create_Task_Application.collaborationModule.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    CommentResponseDTO addComment(Integer taskId, CommentRequestDTO dto);

    List<CommentResponseDTO> getTaskComments(Integer taskId);

    void deleteComment(Integer commentId);
}
