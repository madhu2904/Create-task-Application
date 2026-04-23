package com.batch5.Create_Task_Application.userModule;

import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.entity.UserRole;
import com.batch5.Create_Task_Application.userModule.exceptions.InvalidUserDataException;
import com.batch5.Create_Task_Application.userModule.exceptions.UserAlreadyExistsException;
import com.batch5.Create_Task_Application.userModule.exceptions.UserNotFoundException;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import com.batch5.Create_Task_Application.userModule.repository.UserRoleRepository;
import com.batch5.Create_Task_Application.userModule.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserRole testRole;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .username("jane_doe")
                .password("pass456")
                .email("jane@example.com")
                .fullName("Jane Doe")
                .build();
        testUser.setUserId(1L);
        testUser.setRoles(new ArrayList<>());

        testRole = UserRole.builder()
                .roleName("ROLE_USER")
                .build();
        testRole.setUserRoleId(10);
    }

    // Test 1: createUser — success
    @Test
    void shouldCreateUserSuccessfully() {
        when(userRepository.existsByUsername("jane_doe")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User created = userService.createUser(testUser);

        assertThat(created.getUsername()).isEqualTo("jane_doe");
        verify(userRepository).save(testUser);
    }

    // Test 2: createUser — throws when username is blank
    @Test
    void shouldThrowWhenUsernameIsBlank() {
        testUser.setUsername("  ");

        assertThatThrownBy(() -> userService.createUser(testUser))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessageContaining("Username cannot be empty");
    }

    // Test 3: createUser — throws when email is blank
    @Test
    void shouldThrowWhenEmailIsBlank() {
        testUser.setEmail("");

        assertThatThrownBy(() -> userService.createUser(testUser))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessageContaining("Email cannot be empty");
    }

    // Test 4: createUser — throws when username already exists
    @Test
    void shouldThrowWhenUsernameAlreadyExists() {
        when(userRepository.existsByUsername("jane_doe")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(testUser))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    // Test 5: getUserById — success
    @Test
    void shouldGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User found = userService.getUserById(1L);

        assertThat(found.getEmail()).isEqualTo("jane@example.com");
    }

    // Test 6: getUserById — throws when not found
    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("99");
    }

    // Test 7: updateUser — success
    @Test
    void shouldUpdateUserSuccessfully() {
        User updatedData = User.builder()
                .username("jane_updated")
                .email("jane_updated@example.com")
                .fullName("Jane Updated")
                .password("newpass")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.updateUser(1L, updatedData);

        assertThat(result.getUsername()).isEqualTo("jane_updated");
        verify(userRepository).save(testUser);
    }

    // Test 8: deleteUser — success
    @Test
    void shouldDeleteUserSuccessfully() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    // Test 9: deleteUser — throws when user not found
    @Test
    void shouldThrowWhenDeletingNonExistentUser() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(99L))
                .isInstanceOf(UserNotFoundException.class);
    }

    // Test 10: assignRoleToUser — success
    @Test
    void shouldAssignRoleToUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRoleRepository.findById(10)).thenReturn(Optional.of(testRole));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.assignRoleToUser(1L, 10);

        assertThat(testUser.getRoles()).contains(testRole);
        verify(userRepository).save(testUser);
    }

    // Test 11: assignRoleToUser — throws when role already assigned
    @Test
    void shouldThrowWhenRoleAlreadyAssigned() {
        testUser.getRoles().add(testRole);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRoleRepository.findById(10)).thenReturn(Optional.of(testRole));

        assertThatThrownBy(() -> userService.assignRoleToUser(1L, 10))
                .isInstanceOf(InvalidUserDataException.class)
                .hasMessageContaining("Role already assigned");
    }

    // Test 12: removeRoleFromUser — success
    @Test
    void shouldRemoveRoleFromUser() {
        testUser.getRoles().add(testRole);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRoleRepository.findById(10)).thenReturn(Optional.of(testRole));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.removeRoleFromUser(1L, 10);

        assertThat(testUser.getRoles()).doesNotContain(testRole);
    }

    // Test 13: getRolesOfUser — returns list
    @Test
    void shouldReturnRolesOfUser() {
        testUser.getRoles().add(testRole);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        List<UserRole> roles = userService.getRolesOfUser(1L);

        assertThat(roles).hasSize(1);
        assertThat(roles.get(0).getRoleName()).isEqualTo("ROLE_USER");
    }
}