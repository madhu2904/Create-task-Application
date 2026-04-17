package com.batch5.Create_Task_Application.userModule.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleResponseDTO {

    private Integer userRoleId;
    private String roleName;
}