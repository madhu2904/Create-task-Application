package com.batch5.Create_Task_Application.commonModule.corsconfig;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthVerificationController {

    @GetMapping("/verify")
    public Map<String, Object> verify(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Map.of(
                "authenticated", true,
                "username", authentication.getName(),
                "authorities", authorities
        );
    }
}
