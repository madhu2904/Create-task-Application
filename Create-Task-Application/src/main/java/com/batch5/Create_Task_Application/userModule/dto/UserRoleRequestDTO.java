package com.batch5.Create_Task_Application.userModule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public class UserRoleRequestDTO {

    @NotBlank(message = "Role name cannot be empty")
    private String roleName;

    public UserRoleRequestDTO(String roleName) {
        this.roleName = roleName;
    }

    public UserRoleRequestDTO() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}