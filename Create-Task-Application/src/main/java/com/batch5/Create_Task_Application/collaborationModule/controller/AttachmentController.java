package com.batch5.Create_Task_Application.collaborationModule.controller;

import com.batch5.Create_Task_Application.collaborationModule.dto.*;
import com.batch5.Create_Task_Application.commonModule.ApiResponse;
import com.batch5.Create_Task_Application.collaborationModule.service.AttachmentService;
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
    public ResponseEntity<ApiResponse<AttachmentResponseDTO>> uploadAttachment(
            @PathVariable Integer taskId,
            @Valid @RequestBody AttachmentRequestDTO dto) {

        AttachmentResponseDTO attachmentResponseDTO =
                attachmentService.uploadAttachment(taskId, dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Attachment uploaded successfully",
                        attachmentResponseDTO
                ));
    }

    // To find all the Attachments by taskId
    @GetMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<ApiResponse<List<AttachmentResponseDTO>>> getTaskAttachments(
            @PathVariable Integer taskId) {

        List<AttachmentResponseDTO> attachmentResponseDTOList =
                attachmentService.getTaskAttachments(taskId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Attachments fetched successfully",
                        attachmentResponseDTOList
                ));
    }

    // To Delete an Attachment
    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<ApiResponse<Void>> deleteAttachment(
            @PathVariable Integer attachmentId) {
        attachmentService.deleteAttachment(attachmentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Attachment deleted successfully",
                        null
                ));
    }
}