package com.batch5.Create_Task_Application.collaborationModule.service;

import com.batch5.Create_Task_Application.collaborationModule.dto.AttachmentRequestDTO;
import com.batch5.Create_Task_Application.collaborationModule.dto.AttachmentResponseDTO;
import com.batch5.Create_Task_Application.collaborationModule.entity.Attachment;
import com.batch5.Create_Task_Application.collaborationModule.repository.AttachmentRepository;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.taskModule.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batch5.Create_Task_Application.collaborationModule.exceptions.AttachmentNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public AttachmentResponseDTO uploadAttachment(Integer taskId, AttachmentRequestDTO dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        Attachment attachment = Attachment.builder()
                .fileName(dto.getFileName())
                .filePath(dto.getFilePath())
                .task(task)
                .build();

        Attachment savedAttachment = attachmentRepository.save(attachment);

        return mapToDTO(savedAttachment);
    }

    @Override
    public List<AttachmentResponseDTO> getTaskAttachments(Integer taskId) {
        List<Attachment> attachments = attachmentRepository.findByTask_TaskId(taskId);
        return attachments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAttachment(Integer attachmentId) {
        if (!attachmentRepository.existsById(attachmentId)) {
            throw new AttachmentNotFoundException("Attachment not found with id: " + attachmentId);
        }
        attachmentRepository.deleteById(attachmentId);
    }

    private AttachmentResponseDTO mapToDTO(Attachment attachment) {
        return AttachmentResponseDTO.builder()
                .attachmentId(attachment.getAttachmentId())
                .fileName(attachment.getFileName())
                .filePath(attachment.getFilePath())
                .taskId(attachment.getTask().getTaskId())
                .build();
    }
}
