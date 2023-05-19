package com.example.universityschedule.controller;

import com.example.universityschedule.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void users_returnsView() throws Exception {
       /* List<User> users = new ArrayList<>();
        users.add(new User(1L, "Dmytro", "Tkachuk", "email@gmail.com", "faculty", "department", Role.STUDENT, new Group(1L, "aa-11")));

        given(userServiceImpl.getAll()).willReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", users));

        verify(userServiceImpl).getAll();*/
    }
}