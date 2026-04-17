package com.batch5.Create_Task_Application.collaborationModule.service;

import com.batch5.Create_Task_Application.collaborationModule.dto.AttachmentRequestDTO;
import com.batch5.Create_Task_Application.collaborationModule.dto.AttachmentResponseDTO;

import java.util.List;

public interface AttachmentService {

    AttachmentResponseDTO uploadAttachment(Integer taskId, AttachmentRequestDTO dto);

    List<AttachmentResponseDTO> getTaskAttachments(Integer taskId);

    void deleteAttachment(Integer attachmentId);

}
