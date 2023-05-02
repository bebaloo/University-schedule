package com.example.universityschedule.service;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.GroupMapper;
import com.example.universityschedule.repository.CourseRepository;
import com.example.universityschedule.repository.GroupRepository;
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

@SpringBootTest(classes = GroupService.class)
class GroupServiceTest {
    @Autowired
    private GroupService groupService;
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private GroupMapper groupMapper;

    @Test
    void getAll_returnsCourses() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("aa-11"));
        when(groupRepository.findAll()).thenReturn(groups);

        assertEquals(groupService.getAll(), groups);
        verify(groupRepository).findAll();
    }

    @Test
    void getById_existId_returnsCourse() {
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group("aa-11")));

        assertNotNull(groupService.getById(1L));
        verify(groupRepository).findById(1L);
    }

    @Test
    void getById_nonExistId_returnsNull() {
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertNull(groupService.getById(1L));
        verify(groupRepository).findById(1L);
    }

    @Test
    void update_validGroup_returnsUpdatedStudent() {
        when(groupRepository.save(any(Group.class))).thenReturn(new Group(1L,"aa-11"));
        when(groupRepository.getReferenceById(any(Long.class))).thenReturn(new Group(1L, "aa-11"));

        assertNotNull(groupService.update(new Group(1L, "aa-11")));
        verify(groupRepository).save(any(Group.class));
        verify(groupRepository).getReferenceById(any(Long.class));
    }

    @Test
    void update_invalidGroup_throwsException() {
        when(groupRepository.getReferenceById(any(Long.class))).thenReturn(new Group(1L, "aa-11"));
        when(groupRepository.save(any(Group.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> groupService.update(new Group(1L, "aa-11")));

        assertTrue(exception.getMessage().contains("not updated"));
        verify(groupRepository).save(any(Group.class));
        verify(groupRepository).getReferenceById(any(Long.class));
    }

    @Test
    void create_validGroup_returnsSavedStudent() {
        when(groupRepository.save(any(Group.class))).thenReturn(new Group("aa-11"));

        assertNotNull(groupService.create(new Group("aa-11")));
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void create_invalidGroup_throwsException() {
        when(groupRepository.save(any(Group.class))).thenThrow(IllegalArgumentException.class);

        EntityNotCreatedException exception = assertThrows(EntityNotCreatedException.class,
                () -> groupService.create(new Group("aa-11")));

        assertTrue(exception.getMessage().contains("was not created"));
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void delete_validGroupId_returnsDeletedStudent() {
        when(groupRepository.getReferenceById(any(Long.class))).thenReturn(new Group("aa-11"));

        assertNotNull(groupService.deleteById(1L));
        verify(groupRepository).delete(any(Group.class));
        verify(groupRepository).getReferenceById(any(Long.class));
    }

    @Test
    void delete_invalidGroupId_throwsException() {
        when(groupRepository.getReferenceById(any(Long.class))).thenThrow(IllegalArgumentException.class);

        EntityNotDeletedException exception = assertThrows(EntityNotDeletedException.class,
                () -> groupService.deleteById(1L));

        assertTrue(exception.getMessage().contains("not deleted"));
        verify(groupRepository).getReferenceById(any(Long.class));
    }
}