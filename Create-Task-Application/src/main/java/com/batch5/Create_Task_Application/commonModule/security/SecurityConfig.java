package com.batch5.Create_Task_Application.commonModule.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //  disable CSRF
                .csrf(AbstractHttpConfigurer::disable)

                //  authorization rules
                .authorizeHttpRequests(auth -> auth

                        //  Specific End points

                        .requestMatchers("/api/v1/users/*/projects")
                        .hasRole("PROJECT_MODULE_DEV")

                        .requestMatchers("/api/v1/projects/*/tasks",
                                "/api/v1/users/*/tasks")
                        .hasRole("TASK_MODULE_DEV")

                        .requestMatchers("/api/v1/tasks/status/**",
                                "/api/v1/tasks/priority/**")
                        .hasRole("TASK_MODULE_DEV")

                        .requestMatchers("/api/v1/tasks/*/categories/**")
                        .hasRole("TASK_MODULE_DEV")

                        .requestMatchers("/api/v1/tasks/*/comments",
                                "/api/v1/comments/**",
                                "/api/v1/tasks/*/attachments",
                                "/api/v1/attachments/**")
                        .hasRole("COMMENT_ATTACHMENT_MODULE_DEV")

                        .requestMatchers("/api/v1/users/*/notifications/**")
                        .hasRole("NOTIFICATION_MODULE_DEV")


                        .requestMatchers("/api/v1/projects/**")
                        .hasRole("PROJECT_MODULE_DEV")

                        .requestMatchers("/api/v1/tasks/**",
                                "/api/v1/categories/**")
                        .hasRole("TASK_MODULE_DEV")

                        .requestMatchers("/api/v1/notifications/**")
                        .hasRole("NOTIFICATION_MODULE_DEV")

                        .requestMatchers("/api/v1/users/**",
                                "/api/v1/roles/**")
                        .hasRole("USER_MODULE_DEV")

                        .anyRequest().authenticated()
                )

                //  httpBasic
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}