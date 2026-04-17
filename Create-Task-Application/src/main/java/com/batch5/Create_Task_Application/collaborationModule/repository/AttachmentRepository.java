package com.batch5.Create_Task_Application.CollaborationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.batch5.Create_Task_Application.CollaborationModule.entity.Attachment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    // Alternative: find by Task ID directly
    List<Attachment> findByTask_TaskId(Integer taskId);

}