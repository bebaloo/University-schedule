package com.example.universityschedule.registration;

import com.example.universityschedule.entity.User;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void register(RegistrationRequest request) {
        userService.create(User.builder()
                .firstname(request.firstName())
                .lastname(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.STUDENT)
                .isActive(true)
                .build());
    }
}
