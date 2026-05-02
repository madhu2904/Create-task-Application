package com.batch5.Create_Task_Application.userModule.dto;

import java.util.List;

public class UserResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private List<UserRoleResponseDTO> roles;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserResponseDTO(Long userId, String username, String email, String fullName, String password, List<UserRoleResponseDTO> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = roles;
    }

    // Getters
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public List<UserRoleResponseDTO> getRoles() { return roles; }
}