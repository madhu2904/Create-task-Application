package com.batch5.Create_Task_Application.userModule.dto;

public class UserRoleResponseDTO {

    private Integer userRoleId;
    private String roleName;

    // No-args constructor
    public UserRoleResponseDTO() {
    }

    // All-args constructor
    public UserRoleResponseDTO(Integer userRoleId, String roleName) {
        this.userRoleId = userRoleId;
        this.roleName = roleName;
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
}