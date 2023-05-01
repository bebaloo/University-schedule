package com.example.universityschedule.security;

import com.example.universityschedule.security.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum Role {
    STUDENT(Set.of(Permission.SUBJECT_READ)),
    TUTOR(Set.of(Permission.SUBJECT_READ, Permission.SUBJECT_WRITE)),
    ADMIN(Set.of(Permission.SUBJECT_READ, Permission.SUBJECT_WRITE, Permission.USER_BAN, Permission.USER_UPDATE));

    private final Set<Permission> permissions;
}
