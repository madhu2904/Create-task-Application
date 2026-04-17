package com.batch5.Create_Task_Application.userModule.dto;

import java.util.List;

public class UserResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private List<UserRoleResponseDTO> roles;

    public UserResponseDTO(Long userId, String username, String email, String fullName, List<UserRoleResponseDTO> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
    }

    // Getters
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public List<UserRoleResponseDTO> getRoles() { return roles; }
}