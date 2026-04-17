package com.batch5.Create_Task_Application.CollaborationModule.controller;

import com.batch5.Create_Task_Application.CollaborationModule.dto.*;
import com.batch5.Create_Task_Application.CollaborationModule.service.AttachmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // To Create an Attachment
    @PostMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<CollaborationDTO<AttachmentResponseDTO>> uploadAttachment(
            @PathVariable Integer taskId,
            @Valid @RequestBody AttachmentRequestDTO dto) {

        AttachmentResponseDTO attachmentResponseDTO =
                attachmentService.uploadAttachment(taskId, dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CollaborationDTO<>(
                        HttpStatus.CREATED.value(),
                        "Attachment uploaded successfully",
                        attachmentResponseDTO
                ));
    }

    // To Read an Attachment
    @GetMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<CollaborationDTO<List<AttachmentResponseDTO>>> getTaskAttachments(
            @PathVariable Integer taskId) {

        List<AttachmentResponseDTO> attachmentResponseDTOList =
                attachmentService.getTaskAttachments(taskId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CollaborationDTO<>(
                        HttpStatus.OK.value(),
                        "Attachments fetched successfully",
                        attachmentResponseDTOList
                ));
    }

    // To Delete an Attachment
    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<CollaborationDTO<Void>> deleteAttachment(
            @PathVariable Integer attachmentId) {
        attachmentService.deleteAttachment(attachmentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CollaborationDTO<>(
                        HttpStatus.OK.value(),
                        "Attachment deleted successfully",
                        null
                ));
    }
}