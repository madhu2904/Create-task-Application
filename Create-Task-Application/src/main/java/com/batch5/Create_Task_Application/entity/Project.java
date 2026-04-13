package com.batch5.Create_Task_Application.entity;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String projectName;
    private String description;
    private Date startDate;
    private Date endDate;

    // Many Projects → One User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
