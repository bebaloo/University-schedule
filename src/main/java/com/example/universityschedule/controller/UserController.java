package com.example.universityschedule.controller;

import com.example.universityschedule.entity.User;
import com.example.universityschedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String users(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }
}
