package com.example.universityschedule.service;

import com.example.universityschedule.entity.User;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotFoundException;
import com.example.universityschedule.mapper.UserMapper;
import com.example.universityschedule.repository.GroupRepository;
import com.example.universityschedule.repository.UserRepository;
import com.example.universityschedule.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserMapper userMapper;


    @Test
    void getById_nonExistId_throwsException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.getById(1L));

        assertTrue(exception.getMessage().contains("not found"));
        verify(userRepository).findById(1L);
    }

    @Test
    void create_invalidGroup_throwsException() {
        when(userRepository.save(any(User.class))).thenThrow(IllegalArgumentException.class);

        EntityNotCreatedException exception = assertThrows(EntityNotCreatedException.class,
                () -> userService.create(new User()));

        assertTrue(exception.getMessage().contains("was not created"));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void delete_invalidGroupId_throwsException() {
        when(userRepository.getReferenceById(any(Long.class))).thenThrow(IllegalArgumentException.class);

        EntityNotDeletedException exception = assertThrows(EntityNotDeletedException.class,
                () -> userService.deleteById(1L));

        assertTrue(exception.getMessage().contains("not deleted"));
        verify(userRepository).getReferenceById(any(Long.class));
    }
}