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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
class GroupControllerTest {
    @MockBean
    private GroupService groupService;
    @MockBean
    private CourseService courseService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testGroups() throws Exception {
        List<Group> groups = Arrays.asList(
                new Group(1L, "Group 1"),
                new Group(2L, "Group 2")
        );

        when(groupService.getAll()).thenReturn(groups);

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups"))
                .andExpect(model().attribute("groups", groups));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testAddGroup() throws Exception {
        when(courseService.getById(any(Long.class))).thenReturn(new Course("ss"));

        mockMvc.perform(post("/groups/add")
                        .param("name", "Test Group")
                        .param("courseId", "1")
                        .param("password", "test123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));

        verify(groupService).create(any(Group.class));
        verify(courseService).getById(any(Long.class));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUpdateGroup() throws Exception {
        mockMvc.perform(post("/groups/update/{id}", 1)
                        .param("name", "ss"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));

        verify(groupService).update(any(Group.class));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteGroup() throws Exception {
        mockMvc.perform(post("/groups/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"));
    }
}
