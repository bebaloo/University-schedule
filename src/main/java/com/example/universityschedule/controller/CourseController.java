package com.example.universityschedule.controller;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.service.CourseService;
import com.example.universityschedule.service.GroupService;
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
    private final GroupService groupService;

    @GetMapping
    public String courses(Model model) {
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/{id}")
    public String courseInfo(@PathVariable Long id, Model model) {
        Course course = courseService.getById(id);
        List<Group> groups = groupService.getByCourseId(id);

        model.addAttribute("course", course);
        model.addAttribute("groups", groups);
        return "course-info";
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
