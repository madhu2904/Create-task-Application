package com.batch5.Create_Task_Application.userModule;

import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = userRepository.save(User.builder()
                .username("john_doe")
                .password("secret123")
                .email("john@example.com")
                .fullName("John Doe")
                .build());
    }

    // Test 1: Save and retrieve by ID
    @Test
    void shouldSaveAndFindUserById() {
        Optional<User> found = userRepository.findById(testUser.getUserId());

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("john_doe");
        assertThat(found.get().getEmail()).isEqualTo("john@example.com");
    }

    // Test 2: findByUsername
    @Test
    void shouldFindUserByUsername() {
        Optional<User> found = userRepository.findByUsername("john_doe");

        assertThat(found).isPresent();
        assertThat(found.get().getFullName()).isEqualTo("John Doe");
    }

    // Test 3: findByEmail
    @Test
    void shouldFindUserByEmail() {
        Optional<User> found = userRepository.findByEmail("john@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("john_doe");
    }

    // Test 4: existsByUsername — returns true for existing user
    @Test
    void shouldReturnTrueWhenUsernameExists() {
        boolean exists = userRepository.existsByUsername("john_doe");

        assertThat(exists).isTrue();
    }

    // Test 5: existsByUsername — returns false for unknown user
    @Test
    void shouldReturnFalseWhenUsernameDoesNotExist() {
        boolean exists = userRepository.existsByUsername("ghost_user");

        assertThat(exists).isFalse();
    }

    // Test 6: Update user email and verify
    @Test
    void shouldUpdateUserEmail() {
        testUser.setEmail("updated@example.com");
        userRepository.save(testUser);

        User updated = userRepository.findById(testUser.getUserId()).orElseThrow();
        assertThat(updated.getEmail()).isEqualTo("updated@example.com");
    }

    // Test 7: Delete user
    @Test
    void shouldDeleteUser() {
        userRepository.deleteById(testUser.getUserId());

        Optional<User> found = userRepository.findById(testUser.getUserId());
        assertThat(found).isEmpty();
    }
}