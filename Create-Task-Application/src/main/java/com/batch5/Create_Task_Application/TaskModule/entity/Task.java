package com.batch5.Create_Task_Application.TaskModule.entity;

import com.batch5.Create_Task_Application.CollaborationModule.entity.Comment;
import com.batch5.Create_Task_Application.ProjectModule.entity.Project;
import com.batch5.Create_Task_Application.UserModule.entity.User;
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
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @NotBlank(message = "Task name is required")
    @Size(min = 3, max = 100, message = "Task name must be between 3 and 100 characters")
    @Column(name = "task_name")
    private String taskName;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description should not exceed 500 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @NotBlank(message = "Priority is required")
    @Pattern(regexp = "High|Medium|Low", message = "Priority must be High, Medium, or Low")
    @Column(name = "priority")
    private String priority;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "Pending|In Progress|Completed", message = "Status must be Pending, In Progress, or Completed")
    @Column(name = "status")
    private String status;

    // Project mapping
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @NotNull(message = "Project is required")
    private Project projectId;

    // User mapping
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    // Category mapping
    @ManyToMany
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    // Comments
    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

}
