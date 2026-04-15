package com.batch5.Create_Task_Application.TaskModule.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private String categoryID;

    @Column(name = "category_name")
    private String categoryName;
}
