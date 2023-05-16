package com.example.universityschedule.controller;

import com.example.universityschedule.dto.UserRegistrationDTO;
import com.example.universityschedule.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(UserRegistrationDTO request) {
        registrationService.register(request);
        return "redirect:/login";
    }
}
