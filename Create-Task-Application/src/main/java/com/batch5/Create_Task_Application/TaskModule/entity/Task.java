package com.batch5.Create_Task_Application.TaskModule.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String taskID;

    private String taskName;
    private String description;
    private String dueDate;
    private String priority;
    private String status;
    private String projectID;
    private String userID;

}
