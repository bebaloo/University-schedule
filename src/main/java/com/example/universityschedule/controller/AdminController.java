package com.example.universityschedule.controller;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @GetMapping
    public String users(Model model) {
        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/user/ban/{id}")
    public String banUser(@PathVariable Long id) {
        userService.ban(id);
        return "redirect:/admin";
    }

    @PostMapping("/user/role/{id}")
    public String changeRole(@PathVariable Long id) {
        userService.changeRole(id);
        return "redirect:/admin";
    }
}
