package com.example.universityschedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
