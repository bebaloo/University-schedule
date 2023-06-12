package com.example.universityschedule.controller;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public String users(Model model) {
        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/ban/{id}")
    public String banUser(@PathVariable Long id) {
        userService.ban(id);
        return String.format("redirect:/admin/users/%d", id);
    }

    @PostMapping("/role/{id}")
    public String changeRole(@PathVariable Long id, @RequestParam Role role) {
        userService.changeRole(id, role);
        return String.format("redirect:/admin/users/%d", id);
    }

    @GetMapping("/{id}")
    public String userInfo(@PathVariable Long id, Model model) {
        UserDTO user = userService.getById(id);
        model.addAttribute("user", user);
        return "user-info";
    }
}
