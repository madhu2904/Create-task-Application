package com.batch5.Create_Task_Application.CollaborationModule.repository;

import com.batch5.Create_Task_Application.CollaborationModule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // Get all comments for a given task ID
    List<Comment> findByTaskTaskId(Integer taskId);

    // Get all comments by a specific user ID
    List<Comment> findByUserUserId(Long userId);

    // Get comments for a task ordered by latest first
    List<Comment> findByTaskTaskIdOrderByCreatedAtDesc(Integer taskId);
}
