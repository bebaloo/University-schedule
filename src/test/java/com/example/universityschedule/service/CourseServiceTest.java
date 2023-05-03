package com.example.universityschedule.service;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotFoundException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.CourseMapper;
import com.example.universityschedule.repository.CourseRepository;
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

@SpringBootTest(classes = CourseServiceImpl.class)
class CourseServiceTest {

    @Autowired
    private CourseService courseService;
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private CourseMapper courseMapper;

    @Test
    void getAll_returnsCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("English"));
        when(courseRepository.findAll()).thenReturn(courses);

        assertEquals(courseService.getAll(), courses);
        verify(courseRepository).findAll();
    }

    @Test
    void getById_existId_returnsCourse() {
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Course("English")));

        assertNotNull(courseService.getById(1L));
        verify(courseRepository).findById(1L);
    }

    @Test
    void getById_nonExistId_throwsException() {
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> courseService.getById(1L));

        assertTrue(exception.getMessage().contains("not found"));
        verify(courseRepository).findById(1L);
    }

    @Test
    void update_validGroup_returnsUpdatedStudent() {
        when(courseRepository.save(any(Course.class))).thenReturn(new Course(1L, "English"));
        when(courseRepository.getReferenceById(any(Long.class))).thenReturn(new Course(1L, "English"));

        assertNotNull(courseService.update(new Course(1L, "English")));
        verify(courseRepository).save(any(Course.class));
        verify(courseRepository).getReferenceById(any(Long.class));
    }

    @Test
    void update_invalidGroup_throwsException() {
        when(courseRepository.getReferenceById(any(Long.class))).thenReturn(new Course(1L, "English"));
        when(courseRepository.save(any(Course.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> courseService.update(new Course(1L, "English")));

        assertTrue(exception.getMessage().contains("not updated"));
        verify(courseRepository).save(any(Course.class));
        verify(courseRepository).getReferenceById(any(Long.class));
    }

    @Test
    void create_validGroup_returnsSavedStudent() {
        when(courseRepository.save(any(Course.class))).thenReturn(new Course("English"));

        assertNotNull(courseService.create(new Course("English")));
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void create_invalidGroup_throwsException() {
        when(courseRepository.save(any(Course.class))).thenThrow(IllegalArgumentException.class);

        EntityNotCreatedException exception = assertThrows(EntityNotCreatedException.class,
                () -> courseService.create(new Course("English")));

        assertTrue(exception.getMessage().contains("was not created"));
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void delete_validGroupId_returnsDeletedStudent() {
        when(courseRepository.getReferenceById(any(Long.class))).thenReturn(new Course("English"));

        assertNotNull(courseService.deleteById(1L));
        verify(courseRepository).delete(any(Course.class));
        verify(courseRepository).getReferenceById(any(Long.class));
    }

    @Test
    void delete_invalidGroupId_throwsException() {
        when(courseRepository.getReferenceById(any(Long.class))).thenThrow(IllegalArgumentException.class);

        EntityNotDeletedException exception = assertThrows(EntityNotDeletedException.class,
                () -> courseService.deleteById(1L));

        assertTrue(exception.getMessage().contains("not deleted"));
        verify(courseRepository).getReferenceById(any(Long.class));
    }
}