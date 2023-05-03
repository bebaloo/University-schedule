package com.example.universityschedule.controller;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.service.LessonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LessonController.class)
class LessonControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private LessonServiceImpl lessonServiceImpl;
    @Test
    void lessons_returnsView() throws Exception {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1L, "English", new Group(1L, "aa-11")));

        given(lessonServiceImpl.getAll()).willReturn(lessons);

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons"))
                .andExpect(model().attribute("lessons", lessons));

        verify(lessonServiceImpl).getAll();
    }
}