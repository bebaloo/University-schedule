package com.example.universityschedule.controller;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.security.Status;
import com.example.universityschedule.service.UserServiceImpl;
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
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void users_returnsView() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "Dmytro", "Tkachuk", "email@gmail.com", "faculty", "department", Role.STUDENT, Status.ACTIVE, new Group(1L, "aa-11")));

        given(userServiceImpl.getAll()).willReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", users));

        verify(userServiceImpl).getAll();
    }
}