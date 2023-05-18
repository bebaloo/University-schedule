package com.example.universityschedule.controller;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @GetMapping
    public String courses(Model model) {
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public String addCourse(Course course) {
        courseService.create(course);
        return "redirect:/courses";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);
        return "redirect:/courses";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public String updateCourse(Course course) {
        courseService.update(course);
        return "redirect:/courses";
    }
}
