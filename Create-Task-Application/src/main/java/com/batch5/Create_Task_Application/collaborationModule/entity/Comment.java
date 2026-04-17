package com.batch5.Create_Task_Application.collaborationModule.entity;


import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.userModule.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Builder
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @NotBlank(message = "Comment text cannot be empty")
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @NotNull(message = "Created date is required")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Foreign Key: Many Comments -> One Task
    @NotNull(message = "Task is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // Foreign Key: Many Comments -> One User
    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment() {
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment(Integer commentId, String text, LocalDateTime createdAt, Task task, User user) {
        this.commentId = commentId;
        this.text = text;
        this.createdAt = createdAt;
        this.task = task;
        this.user = user;
    }
}
