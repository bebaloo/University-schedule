package com.example.universityschedule.controller;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.service.CourseService;
import com.example.universityschedule.service.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private GroupService groupService;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testCourses() throws Exception {
        List<Course> courses = Arrays.asList(
                new Course(1L, "Course 1"),
                new Course(2L, "Course 2")
        );

        when(courseService.getAll()).thenReturn(courses);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses"))
                .andExpect(model().attribute("courses", courses));
    }

    @Test
    @WithAnonymousUser
    void testCourseInfo() throws Exception {
        Course course = new Course("ss");
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("ss1"));
        groups.add(new Group("ss2"));

        when(courseService.getById(any(Long.class))).thenReturn(course);
        when(groupService.getByCourseId(any(Long.class))).thenReturn(groups);

        mockMvc.perform(get("/courses/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("course-info"))
                .andExpect(model().attribute("course", course))
                .andExpect(model().attribute("groups", groups));

        verify(courseService).getById(1L);
        verify(groupService).getByCourseId(1L);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testAddCourse() throws Exception {
        mockMvc.perform(post("/courses/add")
                        .param("name", "Course"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService).create(any(Course.class));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteCourse() throws Exception {
        mockMvc.perform(post("/courses/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService).deleteById(1L);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUpdateCourse() throws Exception {
        mockMvc.perform(post("/courses/update")
                        .param("id", "1")
                        .param("name", "Updated Course"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService).update(new Course(1L, "Updated Course"));
    }
}
