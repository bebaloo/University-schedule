package com.example.universityschedule.service.impl;

import com.example.universityschedule.dto.UserRegistrationDTO;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.repository.UserRepository;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRegistrationDTO request) {
        userRepository.save(
                User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.STUDENT)
                .isActive(true)
                .build());
    }
}
