package com.batch5.Create_Task_Application.TaskModule.entity;

import com.batch5.Create_Task_Application.CollaborationModule.entity.Comment;
import com.batch5.Create_Task_Application.ProjectModule.entity.Project;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import jakarta.persistence.*;
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
    private int taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "priority")
    private String priority;

    @Column(name = "status")
    private String status;

    //foreign key mapping
    //Project table bi-directional
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projectId;

    //User table uni-directional
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToMany
    @JoinTable(name = "task_category", joinColumns = @JoinColumn(name="task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

}
