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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}