package com.batch5.Create_Task_Application.TaskModule.entity;

import com.batch5.Create_Task_Application.UserModule.entity.User;
import jakarta.persistence.*;
import lombok.*;

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
    private String taskID;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "priority")
    private String priority;

    @Column(name = "status")
    private String status;

    //foreign key mapping
    //Project table bi-directional
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projectID;

    //User table uni-directional
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userID;

    @ManyToMany
    @JoinTable(name = "task_category", joinColumns = @JoinColumn(name="task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

}
