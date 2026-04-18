package com.batch5.Create_Task_Application.userModule.controller;

import com.batch5.Create_Task_Application.userModule.dto.ApiResponse;
import com.batch5.Create_Task_Application.userModule.dto.UserRoleResponseDTO;
import com.batch5.Create_Task_Application.userModule.entity.UserRole;
import com.batch5.Create_Task_Application.userModule.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserRoleResponseDTO>> createRole(@RequestBody UserRole role) {
        UserRole saved = roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Role created successfully", mapToRoleDTO(saved)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserRoleResponseDTO>>> getAllRoles() {
        List<UserRoleResponseDTO> roles = roleService.getAllRoles()
                .stream()
                .map(this::mapToRoleDTO)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(200, "Roles fetched successfully", roles));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<UserRoleResponseDTO>> getRoleById(@PathVariable Integer roleId) {
        UserRole role = roleService.getRoleById(roleId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Role fetched successfully", mapToRoleDTO(role)));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Role deleted successfully", null));
    }

    // ─── Mapper ───

    private UserRoleResponseDTO mapToRoleDTO(UserRole role) {
        return new UserRoleResponseDTO(role.getUserRoleId(), role.getRoleName());
    }
}