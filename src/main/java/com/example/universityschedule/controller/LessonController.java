package com.example.universityschedule.controller;

import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping
    public String lessons(Model model) {
        List<Lesson> lessons = lessonService.getAll();
        model.addAttribute("lessons", lessons);
        return "lessons";
    }
}
