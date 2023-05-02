package com.example.universityschedule.service;

import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.LessonMapper;
import com.example.universityschedule.repository.GroupRepository;
import com.example.universityschedule.repository.LessonRepository;
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

@SpringBootTest(classes = LessonService.class)
class LessonServiceTest {
    @Autowired
    private LessonService lessonService;
    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private LessonRepository lessonRepository;
    @MockBean
    private LessonMapper lessonMapper;

    @Test
    void getAll_returnsCourses() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("English"));
        when(lessonRepository.findAll()).thenReturn(lessons);

        assertEquals(lessonService.getAll(), lessons);
        verify(lessonRepository).findAll();
    }

    @Test
    void getById_existId_returnsCourse() {
        when(lessonRepository.findById(any(Long.class))).thenReturn(Optional.of(new Lesson("English")));

        assertNotNull(lessonService.getById(1L));
        verify(lessonRepository).findById(1L);
    }

    @Test
    void getById_nonExistId_returnsNull() {
        when(lessonRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertNull(lessonService.getById(1L));
        verify(lessonRepository).findById(1L);
    }

    @Test
    void update_validGroup_returnsUpdatedStudent() {
        when(lessonRepository.save(any(Lesson.class))).thenReturn(new Lesson(1L, "English"));
        when(lessonRepository.getReferenceById(any(Long.class))).thenReturn(new Lesson(1L, "English"));

        assertNotNull(lessonService.update(new Lesson(1L, "English")));
        verify(lessonRepository).save(any(Lesson.class));
        verify(lessonRepository).getReferenceById(any(Long.class));
    }

    @Test
    void update_invalidGroup_throwsException() {
        when(lessonRepository.getReferenceById(any(Long.class))).thenReturn(new Lesson(1L, "English"));
        when(lessonRepository.save(any(Lesson.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> lessonService.update(new Lesson(1L, "English")));

        assertTrue(exception.getMessage().contains("not updated"));
        verify(lessonRepository).save(any(Lesson.class));
        verify(lessonRepository).getReferenceById(any(Long.class));
    }

    @Test
    void create_validGroup_returnsSavedStudent() {
        when(lessonRepository.save(any(Lesson.class))).thenReturn(new Lesson("English"));

        assertNotNull(lessonService.create(new Lesson("English")));
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void create_invalidGroup_throwsException() {
        when(lessonRepository.save(any(Lesson.class))).thenThrow(IllegalArgumentException.class);

        EntityNotCreatedException exception = assertThrows(EntityNotCreatedException.class,
                () -> lessonService.create(new Lesson("English")));

        assertTrue(exception.getMessage().contains("was not created"));
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void delete_validGroupId_returnsDeletedStudent() {
        when(lessonRepository.getReferenceById(any(Long.class))).thenReturn(new Lesson("English"));

        assertNotNull(lessonService.deleteById(1L));
        verify(lessonRepository).delete(any(Lesson.class));
        verify(lessonRepository).getReferenceById(any(Long.class));
    }

    @Test
    void delete_invalidGroupId_throwsException() {
        when(lessonRepository.getReferenceById(any(Long.class))).thenThrow(IllegalArgumentException.class);

        EntityNotDeletedException exception = assertThrows(EntityNotDeletedException.class,
                () -> lessonService.deleteById(1L));

        assertTrue(exception.getMessage().contains("not deleted"));
        verify(lessonRepository).getReferenceById(any(Long.class));
    }
}