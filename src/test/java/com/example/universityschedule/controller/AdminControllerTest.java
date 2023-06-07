package com.example.universityschedule.controller;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.security.Role;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUsers() throws Exception {
        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO(1L, "User 1", "user", "ss", "ss", "ss", true, Role.STUDENT, new Group()));
        users.add(new UserDTO(2L, "User 2", "user", "ss", "ss", "ss", true, Role.STUDENT, new Group()));
        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testBanUser() throws Exception {
        mockMvc.perform(post("/admin/users/ban/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService).ban(1L);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testChangeRole() throws Exception {
        mockMvc.perform(post("/admin/users/role/{id}", 1L)
                        .param("role", Role.ADMIN.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService).changeRole(1L, Role.ADMIN);
    }


    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUserInfo() throws Exception {
        UserDTO user = new UserDTO(1L, "User 1", "user", "ss", "ss", "ss", true, Role.STUDENT, new Group());
        when(userService.getById(any(Long.class))).thenReturn(user);
        mockMvc.perform(get("/admin/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("user-info"))
                .andExpect(model().attribute("user", user));
    }

}
