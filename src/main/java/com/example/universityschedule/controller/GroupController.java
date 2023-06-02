package com.example.universityschedule.controller;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.service.CourseService;
import com.example.universityschedule.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final CourseService courseService;
    private final UserDetailsService userDetailsService;

    @GetMapping
    public String groups(Model model) {
        List<Group> groups = groupService.getAll();
        List<Course> courses = courseService.getAll();

        model.addAttribute("groups", groups);
        model.addAttribute("courses", courses);
        return "groups";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public String addGroup(@RequestParam String name, @RequestParam Long courseId, @RequestParam String password) {
        Course course = courseService.getById(courseId);
        Group group = new Group(name, password, course);

        groupService.create(group);
        return "redirect:/groups";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public String updateGroup(@RequestBody Group group) {
        groupService.update(group);
        return "redirect:/groups";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
        return "redirect:/groups";
    }

    @PostMapping("/{id}/students/add")
    public String addStudent(Principal principal, @PathVariable Long id, @RequestParam String password) {

        if (groupService.getById(id).getPassword().equals(password)) {
            User user = (User) userDetailsService.loadUserByUsername(principal.getName());
            groupService.addStudent(id, user.getId());
        }

        return "redirect:/groups";
    }
}
