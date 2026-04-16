package com.batch5.Create_Task_Application.CollaborationModule.controller;

import com.batch5.Create_Task_Application.CollaborationModule.dto.AttachmentRequestDTO;
import com.batch5.Create_Task_Application.CollaborationModule.dto.AttachmentResponseDTO;
import com.batch5.Create_Task_Application.CollaborationModule.service.AttachmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<AttachmentResponseDTO> uploadAttachment(@PathVariable Integer taskId,
            @Valid @RequestBody AttachmentRequestDTO dto) {
        AttachmentResponseDTO uploadedAttachment = attachmentService.uploadAttachment(taskId, dto);
        return new ResponseEntity<>(uploadedAttachment,HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<List<AttachmentResponseDTO>> getTaskAttachments(@PathVariable Integer taskId) {
        List<AttachmentResponseDTO> attachments = attachmentService.getTaskAttachments(taskId);
        return ResponseEntity.ok(attachments);
    }

    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Integer attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.noContent().build();
    }

}
