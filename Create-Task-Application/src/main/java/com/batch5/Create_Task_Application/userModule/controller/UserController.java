package com.batch5.Create_Task_Application.userModule.controller;
import com.batch5.Create_Task_Application.commonModule.ApiResponse;
import com.batch5.Create_Task_Application.userModule.dto.UserResponseDTO;
import com.batch5.Create_Task_Application.userModule.dto.UserRoleResponseDTO;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    //Create User
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@Valid @RequestBody User user) {
        User saved = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "User created successfully", mapToUserDTO(saved)));
    }

    //Get All Users
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers()
                .stream()
                .map(this::mapToUserDTO)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(200, "Users fetched successfully", users));
    }

    //Get User By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "User fetched successfully", mapToUserDTO(user)));
    }

    //Update User By id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        return ResponseEntity.ok(new ApiResponse<>(200, "User updated successfully", mapToUserDTO(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "User deleted successfully", null));
    }

    // ─── User-Role Mapping ───

    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<String>> assignRole(@PathVariable Long userId, @PathVariable Integer roleId) {
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Role assigned successfully", null));
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<String>> removeRole(@PathVariable Long userId, @PathVariable Integer roleId) {
        userService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Role removed successfully", null));
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<ApiResponse<List<UserRoleResponseDTO>>> getRolesOfUser(@PathVariable Long userId) {
        List<UserRoleResponseDTO> roles = userService.getRolesOfUser(userId)
                .stream()
                .map(r -> new UserRoleResponseDTO(r.getUserRoleId(), r.getRoleName()))
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(200, "Roles fetched successfully", roles));
    }

    // ─── Mapper ───

    private UserResponseDTO mapToUserDTO(User user) {
        List<UserRoleResponseDTO> roles = user.getRoles()
                .stream()
                .map(r -> new UserRoleResponseDTO(r.getUserRoleId(), r.getRoleName()))
                .toList();
        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                roles
        );
    }
}