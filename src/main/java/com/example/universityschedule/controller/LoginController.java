package com.example.universityschedule.controller;

import com.example.universityschedule.entity.User;
import com.example.universityschedule.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String userInterface(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin";
        } else if (user.getRole() == Role.TUTOR) {
            return "redirect:/tutor";
        } else {
            return "redirect:/student";
        }
    }
}
