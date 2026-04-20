package com.batch5.Create_Task_Application.userModule;

import com.batch5.Create_Task_Application.userModule.entity.UserRole;
import com.batch5.Create_Task_Application.userModule.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    private UserRole testRole;

    @BeforeEach
    void setUp() {
        userRoleRepository.deleteAll();

        testRole = userRoleRepository.save(UserRole.builder()
                .roleName("ROLE_ADMIN")
                .build());
    }

    // Test 1: Save and retrieve by ID
    @Test
    void shouldSaveAndFindRoleById() {
        Optional<UserRole> found = userRoleRepository.findById(testRole.getUserRoleId());

        assertThat(found).isPresent();
        assertThat(found.get().getRoleName()).isEqualTo("ROLE_ADMIN");
    }

    // Test 2: findByRoleName
    @Test
    void shouldFindRoleByName() {
        Optional<UserRole> found = userRoleRepository.findByRoleName("ROLE_ADMIN");

        assertThat(found).isPresent();
        assertThat(found.get().getRoleName()).isEqualTo("ROLE_ADMIN");
    }

    // Test 3: findByRoleName — returns empty for unknown role
    @Test
    void shouldReturnEmptyWhenRoleNameNotFound() {
        Optional<UserRole> found = userRoleRepository.findByRoleName("ROLE_UNKNOWN");

        assertThat(found).isEmpty();
    }

    // Test 4: existsByRoleName — returns true for existing role
    @Test
    void shouldReturnTrueWhenRoleExists() {
        boolean exists = userRoleRepository.existsByRoleName("ROLE_ADMIN");

        assertThat(exists).isTrue();
    }

    // Test 5: existsByRoleName — returns false for non-existing role
    @Test
    void shouldReturnFalseWhenRoleDoesNotExist() {
        boolean exists = userRoleRepository.existsByRoleName("ROLE_GHOST");

        assertThat(exists).isFalse();
    }

    // Test 6: findAll — returns all saved roles
    @Test
    void shouldReturnAllRoles() {
        userRoleRepository.save(UserRole.builder().roleName("ROLE_USER").build());

        List<UserRole> roles = userRoleRepository.findAll();

        assertThat(roles).hasSize(2);
    }

    // Test 7: Delete role
    @Test
    void shouldDeleteRole() {
        userRoleRepository.deleteById(testRole.getUserRoleId());

        Optional<UserRole> found = userRoleRepository.findById(testRole.getUserRoleId());
        assertThat(found).isEmpty();
    }
}