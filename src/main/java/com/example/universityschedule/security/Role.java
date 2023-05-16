package com.example.universityschedule.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN,
    TUTOR,
    STUDENT
}
