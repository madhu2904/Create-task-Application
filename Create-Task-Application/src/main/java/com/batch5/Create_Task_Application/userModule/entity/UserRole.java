package com.batch5.Create_Task_Application.userModule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.batch5.Create_Task_Application.userModule.entity.User;

import java.util.List;

@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_RoleID")
    private Integer userRoleId;

    @NotBlank(message = "Role name is required")
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    // No-args constructor
    public UserRole() {
    }

    // All-args constructor
    public UserRole(Integer userRoleId, String roleName, List<User> users) {
        this.userRoleId = userRoleId;
        this.roleName = roleName;
        this.users = users;
    }

    // Getters and Setters

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}