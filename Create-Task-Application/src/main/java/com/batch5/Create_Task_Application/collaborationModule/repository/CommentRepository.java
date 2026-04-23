package com.batch5.Create_Task_Application.collaborationModule.repository;

import com.batch5.Create_Task_Application.collaborationModule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.task.taskId = :taskId ORDER BY c.createdAt DESC")
    List<Comment> findCommentsByTaskId_OrderByDate(@Param("taskId") Integer taskId);

}
