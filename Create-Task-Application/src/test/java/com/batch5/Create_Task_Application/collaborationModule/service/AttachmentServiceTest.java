package com.batch5.Create_Task_Application.collaborationModule.service;

import com.batch5.Create_Task_Application.collaborationModule.dto.AttachmentRequestDTO;
import com.batch5.Create_Task_Application.collaborationModule.dto.AttachmentResponseDTO;
import com.batch5.Create_Task_Application.collaborationModule.entity.Attachment;
import com.batch5.Create_Task_Application.collaborationModule.exceptions.AttachmentNotFoundException;
import com.batch5.Create_Task_Application.collaborationModule.repository.AttachmentRepository;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.exceptions.TaskNotFoundException;
import com.batch5.Create_Task_Application.taskModule.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttachmentServiceTest {

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private AttachmentServiceImpl attachmentService;

    private Task mockTask;
    private Attachment mockAttachment;

    @BeforeEach
    void setUp() {
        mockTask = new Task();
        mockTask.setTaskId(1);
        mockTask.setTaskName("Test Task");

        mockAttachment = new Attachment();
        mockAttachment.setAttachmentId(1);
        mockAttachment.setFileName("report.pdf");
        mockAttachment.setFilePath("/files/report.pdf");
        mockAttachment.setTask(mockTask);
    }

    // Test 1: Upload attachment successfully
    @Test
    void uploadAttachment_ShouldReturnDTO_WhenTaskExists() {
        AttachmentRequestDTO dto = new AttachmentRequestDTO();
        dto.setFileName("report.pdf");
        dto.setFilePath("/files/report.pdf");

        when(taskRepository.findById(1)).thenReturn(Optional.of(mockTask));
        when(attachmentRepository.save(any(Attachment.class))).thenReturn(mockAttachment);

        AttachmentResponseDTO result = attachmentService.uploadAttachment(1, dto);

        assertThat(result).isNotNull();
        assertThat(result.getFileName()).isEqualTo("report.pdf");
        assertThat(result.getTaskId()).isEqualTo(1);
        verify(attachmentRepository, times(1)).save(any(Attachment.class));
    }

    // Test 2 (Negative): Upload fails when task not found
    @Test
    void uploadAttachment_ShouldThrowTaskNotFoundException_WhenTaskDoesNotExist() {
        AttachmentRequestDTO dto = new AttachmentRequestDTO();
        dto.setFileName("report.pdf");
        dto.setFilePath("/files/report.pdf");

        when(taskRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> attachmentService.uploadAttachment(99, dto))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task not found with id: 99");

        verify(attachmentRepository, never()).save(any());
    }

    // Test 3: Get attachments returns list for valid task
    @Test
    void getTaskAttachments_ShouldReturnList_WhenAttachmentsExist() {
        when(attachmentRepository.findByTask_TaskId(1)).thenReturn(List.of(mockAttachment));

        List<AttachmentResponseDTO> result = attachmentService.getTaskAttachments(1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFileName()).isEqualTo("report.pdf");
    }

    // Test 4 (Negative): Get attachments returns empty list when none exist
    @Test
    void getTaskAttachments_ShouldReturnEmptyList_WhenNoAttachmentsExist() {
        when(attachmentRepository.findByTask_TaskId(1)).thenReturn(List.of());

        List<AttachmentResponseDTO> result = attachmentService.getTaskAttachments(1);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    // Test 5: Delete attachment successfully
    @Test
    void deleteAttachment_ShouldDelete_WhenAttachmentExists() {
        when(attachmentRepository.existsById(1)).thenReturn(true);

        attachmentService.deleteAttachment(1);

        verify(attachmentRepository, times(1)).deleteById(1);
    }

    // Test 6 (Negative): Delete throws when attachment not found
    @Test
    void deleteAttachment_ShouldThrowAttachmentNotFoundException_WhenNotFound() {
        when(attachmentRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> attachmentService.deleteAttachment(99))
                .isInstanceOf(AttachmentNotFoundException.class)
                .hasMessageContaining("Attachment not found with id: 99");

        verify(attachmentRepository, never()).deleteById(any());
    }
}