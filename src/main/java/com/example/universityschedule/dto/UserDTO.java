package com.example.universityschedule.dto;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.security.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UserDTO(
        Long id,
        String firstname,
        String lastname,
        String email,
        String faculty,
        String department,
        boolean isActive,
        @Enumerated(EnumType.STRING)
        Role role,
        Group group
) {
}
