package com.batch5.Create_Task_Application.CollaborationModule.service;

import com.batch5.Create_Task_Application.CollaborationModule.dto.AttachmentRequestDTO;
import com.batch5.Create_Task_Application.CollaborationModule.dto.AttachmentResponseDTO;

import java.util.List;

public interface AttachmentService {

    AttachmentResponseDTO uploadAttachment(Integer taskId, AttachmentRequestDTO dto);

    List<AttachmentResponseDTO> getTaskAttachments(Integer taskId);
    
    void deleteAttachment(Integer attachmentId);
}


    

    