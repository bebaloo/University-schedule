package com.example.universityschedule.controller;

import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.mapper.UserMapper;
import com.example.universityschedule.service.GroupService;
import com.example.universityschedule.service.LessonService;
import com.example.universityschedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.DayOfWeek;
import java.util.List;

@Controller
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final GroupService groupService;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public String lessons(Principal principal, Model model) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        List<Lesson> lessons = lessonService.getByUser(user);


        model.addAttribute("daysOfWeek", DayOfWeek.values());
        model.addAttribute("lessons", lessons);
        model.addAttribute("tutors", userService.getTutors());
        model.addAttribute("groups", groupService.getAll());
        return "lessons";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public String createLesson(Lesson lesson, Long tutorId, Long groupId) {
        lesson.setTutor(userMapper.fromDto(userService.getById(tutorId)));
        lesson.setGroup(groupService.getById(groupId));

        lessonService.create(lesson);
        return "redirect:/lessons";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public String updateLesson(Lesson lesson, Long tutorId, Long groupId) {
        lesson.setTutor(userMapper.fromDto(userService.getById(tutorId)));
        lesson.setGroup(groupService.getById(groupId));

        lessonService.update(lesson);
        return String.format("redirect:/lessons/%d", lesson.getId());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        lessonService.deleteById(id);
        return "redirect:/lessons";
    }

    @GetMapping("/{id}")
    public String lessonInfo(@PathVariable Long id, Model model) {
        Lesson lesson = lessonService.getById(id);
        model.addAttribute("lesson", lesson);
        model.addAttribute("tutors", userService.getTutors());
        model.addAttribute("groups", groupService.getAll());
        return "lesson-info";
    }

}
