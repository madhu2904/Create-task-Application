package com.batch5.Create_Task_Application.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "userrole")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
