package com.batch5.Create_Task_Application.UserModule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleRequestDTO {

    @NotBlank(message = "Role name cannot be empty")
    private String roleName;
}