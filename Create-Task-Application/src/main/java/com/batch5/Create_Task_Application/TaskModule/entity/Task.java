package com.batch5.Create_Task_Application.TaskModule.entity;

import com.batch5.Create_Task_Application.CollaborationModule.entity.Comment;
import com.batch5.Create_Task_Application.ProjectModule.entity.Project;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @NotBlank(message = "Task name cannot be empty")
    @Size(min = 3, max = 100, message = "Task name must be between 3 and 100 characters")
    @Column(name = "task_name")
    private String taskName;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @NotBlank(message = "Priority is required")
    @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority must be LOW, MEDIUM, or HIGH")
    @Column(name = "priority")
    private String priority;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "TODO|IN_PROGRESS|DONE", message = "Status must be TODO, IN_PROGRESS, or DONE")
    @Column(name = "status")
    private String status;

    // Project (FK)
    @NotNull(message = "Project must be assigned")
    @ManyToOne
    @JsonBackReference//repeated loops between project entity and task entity
    @JoinColumn(name = "project_id")
    private Project project;

    // User (FK)
    @NotNull(message = "User must be assigned")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Categories
    @NotEmpty(message = "At least one category must be selected")
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name="task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    public Task() {
    }
    public Task(Integer taskId, String taskName, String description, LocalDateTime dueDate, String priority, String status, Project project, User user, Set<Category> categories, List<Comment> comments) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.project = project;
        this.user = user;
        this.categories = categories;
        this.comments = comments;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


}