package com.example.universityschedule.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    SUBJECT_READ("subject:read"),
    SUBJECT_WRITE("subject:write"),
    USER_READ("user:read"),
    USER_BAN("user:ban"),
    USER_UPDATE("user:update");
    private final String permission;
}
