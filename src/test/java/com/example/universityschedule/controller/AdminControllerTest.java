package com.example.universityschedule.controller;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminInterface() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void users() throws Exception {
        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO(1L,
                "Dmytro",
                "Tkachuk",
                "email@gmail.com",
                "faculty",
                "department",
                true,
                Role.STUDENT,
                new Group(1L, "aa-11")));

        given(userService.getAll()).willReturn(users);

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", users));

        verify(userService).getAll();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void banUser() throws Exception {
        mockMvc.perform(post("/admin/users/ban/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService).ban(1L);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void changeRole() throws Exception {
        mockMvc.perform(post("/admin/users/role/{id}", "1")
                        .param("role", Role.STUDENT.name()))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService).changeRole(1L, Role.STUDENT);
    }
}