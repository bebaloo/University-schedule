package com.example.universityschedule.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tutor")
@PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
public class TutorController {
    @GetMapping
    public String tutorInterface() {
        return "tutor";
    }
}
