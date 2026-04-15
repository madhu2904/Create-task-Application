package com.batch5.Create_Task_Application.CollaborationModule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentResponseDTO {

    private Integer attachmentId;
    private String fileName;
    private String filePath;
    private Integer taskId;

}
