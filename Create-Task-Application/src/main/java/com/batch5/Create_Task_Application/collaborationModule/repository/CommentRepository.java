package com.batch5.Create_Task_Application.collaborationModule.repository;

import com.batch5.Create_Task_Application.collaborationModule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // Get all comments by a specific user ID
    List<Comment> findByUserUserId(Long userId);

    // Custom JPQL query — backs GET /api/v1/tasks/{taskId}/comments
    @Query("SELECT c FROM Comment c WHERE c.task.taskId = :taskId ORDER BY c.createdAt DESC")
    List<Comment> findCommentsByTaskIdOrderByDate(@Param("taskId") Integer taskId);

}
