package com.batch5.Create_Task_Application.UserModule.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponseDTO {

    private Long userId;
    private String username;
    private String email;
    private String fullName;

    private List<String> roles;
}