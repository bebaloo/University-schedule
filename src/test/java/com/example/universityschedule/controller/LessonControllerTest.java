package com.example.universityschedule.controller;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.mapper.UserMapper;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.service.GroupService;
import com.example.universityschedule.service.LessonService;
import com.example.universityschedule.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    @WithMockUser
    void testLessons() throws Exception {
        List<Lesson> lessons = new ArrayList<>();

        given(lessonService.getByUser(any(User.class))).willReturn(lessons);

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons"))
                .andExpect(model().attribute("lessons", lessons))
                .andExpect(model().attributeExists("tutors"))
                .andExpect(model().attributeExists("groups"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testCreateLesson() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        given(userService.getById(any(Long.class))).willReturn(new UserDTO(1L, "User 1", "user", "ss", "ss", "ss", true, Role.STUDENT, new Group()));
        given(groupService.getById(any(Long.class))).willReturn(new Group());
        given(lessonService.create(any(Lesson.class))).willReturn(lesson);

        mockMvc.perform(post("/lessons/create")
                                .param("tutorId", "1")
                                .param("groupId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lessons"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUpdateLesson() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        given(userService.getById(any(Long.class))).willReturn(new UserDTO(1L, "User 1", "user", "ss", "ss", "ss", true, Role.STUDENT, new Group()));
        given(groupService.getById(any(Long.class))).willReturn(new Group());
        given(lessonService.update(any(Lesson.class))).willReturn(lesson);

        mockMvc.perform(post("/lessons/update")
                                .param("id", "1")
                                .param("tutorId", "1")
                                .param("groupId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lessons/1"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteLesson() throws Exception {
        mockMvc.perform(post("/lessons/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lessons"));

        verify(lessonService, times(1)).deleteById(1L);
    }
}
