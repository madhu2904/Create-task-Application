package com.batch5.Create_Task_Application.TaskModule.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String TaskID;

    private String TaskName;
    private String Description;
    private String DueDate;
    private String Priority;
    private String Status;
    private String ProjectID;
    private String UserID;

}
