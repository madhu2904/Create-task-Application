package com.batch5.Create_Task_Application.commonModule.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AdminConfig {

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails userDev = User.builder()
                .username("user_dev")
                .password("$2a$12$9vlvlm7o7dL2RXro82KUVuubY42wqTJeX2K1WemiXQZAtlxghJ5ha")
                .roles("USER_MODULE_DEV")
                .build();

        UserDetails projectDev = User.builder()
                .username("project_dev")
                .password("$2a$12$9vlvlm7o7dL2RXro82KUVuubY42wqTJeX2K1WemiXQZAtlxghJ5ha")
                .roles("PROJECT_MODULE_DEV")
                .build();

        UserDetails taskDev = User.builder()
                .username("task_dev")
                .password("$2a$12$9vlvlm7o7dL2RXro82KUVuubY42wqTJeX2K1WemiXQZAtlxghJ5ha")
                .roles("TASK_MODULE_DEV")
                .build();

        UserDetails commentDev = User.builder()
                .username("comment_dev")
                .password("$2a$12$9vlvlm7o7dL2RXro82KUVuubY42wqTJeX2K1WemiXQZAtlxghJ5ha")
                .roles("COMMENT_ATTACHMENT_MODULE_DEV")
                .build();

        UserDetails notificationDev = User.builder()
                .username("notification_dev")
                .password("$2a$12$9vlvlm7o7dL2RXro82KUVuubY42wqTJeX2K1WemiXQZAtlxghJ5ha")
                .roles("NOTIFICATION_MODULE_DEV")
                .build();

        return new InMemoryUserDetailsManager(
                userDev, projectDev, taskDev, commentDev, notificationDev
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}