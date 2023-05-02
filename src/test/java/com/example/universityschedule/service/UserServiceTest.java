package com.example.universityschedule.service;

import com.example.universityschedule.entity.User;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.UserMapper;
import com.example.universityschedule.repository.GroupRepository;
import com.example.universityschedule.repository.TimetableRepository;
import com.example.universityschedule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserService.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private TimetableRepository timetableRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserMapper userMapper;

    @Test
    void getAll_returnsCourses() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        assertEquals(userService.getAll(), users);
        verify(userRepository).findAll();
    }

    @Test
    void getById_existId_returnsCourse() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new User()));

        assertNotNull(userService.getById(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    void getById_nonExistId_returnsNull() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertNull(userService.getById(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    void update_validGroup_returnsUpdatedStudent() {
        when(userRepository.save(any(User.class))).thenReturn(new User(1L));
        when(userRepository.getReferenceById(any(Long.class))).thenReturn(new User(1L));

        assertNotNull(userService.update(new User(1L)));
        verify(userRepository).save(any(User.class));
        verify(userRepository).getReferenceById(any(Long.class));
    }

    @Test
    void update_invalidGroup_throwsException() {
        when(userRepository.getReferenceById(any(Long.class))).thenReturn(new User(1L));
        when(userRepository.save(any(User.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> userService.update(new User(1L)));

        assertTrue(exception.getMessage().contains("not updated"));
        verify(userRepository).save(any(User.class));
        verify(userRepository).getReferenceById(any(Long.class));
    }

    @Test
    void create_validGroup_returnsSavedStudent() {
        when(userRepository.save(any(User.class))).thenReturn(new User());

        assertNotNull(userService.create(new User()));
        verify(userRepository).save(any(User.class));
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
    void delete_validGroupId_returnsDeletedStudent() {
        when(userRepository.getReferenceById(any(Long.class))).thenReturn(new User());

        assertNotNull(userService.deleteById(1L));
        verify(userRepository).delete(any(User.class));
        verify(userRepository).getReferenceById(any(Long.class));
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