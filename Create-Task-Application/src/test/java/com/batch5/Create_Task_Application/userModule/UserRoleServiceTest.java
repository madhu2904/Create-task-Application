package com.batch5.Create_Task_Application.userModule;

import com.batch5.Create_Task_Application.userModule.entity.UserRole;
import com.batch5.Create_Task_Application.userModule.exceptions.InvalidUserDataException;
import com.batch5.Create_Task_Application.userModule.exceptions.RoleNotFoundException;
import com.batch5.Create_Task_Application.userModule.repository.UserRoleRepository;
import com.batch5.Create_Task_Application.userModule.service.UserRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserRoleRepository roleRepository;

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    private UserRole testRole;

    @BeforeEach
    void setUp() {
        testRole = UserRole.builder()
                .roleName("ROLE_MANAGER")
                .build();
        testRole.setUserRoleId(5);
    }

    // Test 1: createRole — success
    @Test
    void shouldCreateRoleSuccessfully() {
        when(roleRepository.save(any(UserRole.class))).thenReturn(testRole);

        UserRole created = userRoleService.createRole(testRole);

        assertThat(created.getRoleName()).isEqualTo("ROLE_MANAGER");
        verify(roleRepository).save(testRole);
    }

    // Test 2: createRole — throws when role name is blank
    @Test
    void shouldThrowWhenRoleNameIsBlank() {
        testRole.setRoleName("  ");

        assertThatThrownBy(() -> userRoleService.createRole(testRole))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessageContaining("Role name cannot be empty");
    }

    // Test 3: createRole — throws when role name is null
    @Test
    void shouldThrowWhenRoleNameIsNull() {
        testRole.setRoleName(null);

        assertThatThrownBy(() -> userRoleService.createRole(testRole))
                .isInstanceOf(InvalidUserDataException.class);
    }

    // Test 4: getAllRoles — returns list
    @Test
    void shouldReturnAllRoles() {
        when(roleRepository.findAll()).thenReturn(List.of(testRole,
                UserRole.builder().roleName("ROLE_USER").build()));

        List<UserRole> roles = userRoleService.getAllRoles();

        assertThat(roles).hasSize(2);
    }

    // Test 5: getRoleById — success
    @Test
    void shouldGetRoleById() {
        when(roleRepository.findById(5)).thenReturn(Optional.of(testRole));

        UserRole found = userRoleService.getRoleById(5);

        assertThat(found.getRoleName()).isEqualTo("ROLE_MANAGER");
    }

    // Test 6: getRoleById — throws when not found
    @Test
    void shouldThrowWhenRoleNotFoundById() {
        when(roleRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRoleService.getRoleById(99))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessageContaining("99");
    }

    // Test 7: deleteRole — success
    @Test
    void shouldDeleteRoleSuccessfully() {
        when(roleRepository.findById(5)).thenReturn(Optional.of(testRole));
        doNothing().when(roleRepository).delete(testRole);

        userRoleService.deleteRole(5);

        verify(roleRepository).delete(testRole);
    }

    // Test 8: deleteRole — throws when role not found
    @Test
    void shouldThrowWhenDeletingNonExistentRole() {
        when(roleRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRoleService.deleteRole(99))
                .isInstanceOf(RoleNotFoundException.class);
    }
}