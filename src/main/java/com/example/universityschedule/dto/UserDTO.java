package com.example.universityschedule.dto;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.security.Role;

public record UserDTO(
        Long id,
        String firstname,
        String lastname,
        String email,
        String faculty,
        String department,
        boolean isActive,
        Role role,
        Group group
) {
}
