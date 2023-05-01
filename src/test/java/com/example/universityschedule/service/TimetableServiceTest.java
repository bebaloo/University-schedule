package com.example.universityschedule.service;

import com.example.universityschedule.entity.Timetable;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.TimetableMapper;
import com.example.universityschedule.repository.TimetableRepository;
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

@SpringBootTest(classes = TimetableService.class)
class TimetableServiceTest {
    @Autowired
    private TimetableService timetableService;
    @MockBean
    private TimetableRepository timetableRepository;
    @MockBean
    private TimetableMapper timetableMapper;

    @Test
    void getAll_returnsCourses() {
        List<Timetable> timetables = new ArrayList<>();
        timetables.add(new Timetable());
        when(timetableRepository.findAll()).thenReturn(timetables);

        assertEquals(timetableService.getAll(), timetables);
        verify(timetableRepository).findAll();
    }

    @Test
    void getById_existId_returnsCourse() {
        when(timetableRepository.findById(any(Long.class))).thenReturn(Optional.of(new Timetable(1L)));

        assertNotNull(timetableService.getById(1L));
        verify(timetableRepository).findById(1L);
    }

    @Test
    void getById_nonExistId_returnsNull() {
        when(timetableRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertNull(timetableService.getById(1L));
        verify(timetableRepository).findById(1L);
    }

    @Test
    void update_validGroup_returnsUpdatedStudent() {
        when(timetableRepository.save(any(Timetable.class))).thenReturn(new Timetable(1L));
        when(timetableRepository.getReferenceById(any(Long.class))).thenReturn(new Timetable(1L));

        assertNotNull(timetableService.update(new Timetable(1L)));
        verify(timetableRepository).save(any(Timetable.class));
        verify(timetableRepository).getReferenceById(any(Long.class));
    }

    @Test
    void update_invalidGroup_throwsException() {
        when(timetableRepository.getReferenceById(any(Long.class))).thenReturn(new Timetable(1L));
        when(timetableRepository.save(any(Timetable.class))).thenThrow(IllegalArgumentException.class);

        EntityNotUpdatedException exception = assertThrows(EntityNotUpdatedException.class,
                () -> timetableService.update(new Timetable(1L)));

        assertTrue(exception.getMessage().contains("not updated"));
        verify(timetableRepository).save(any(Timetable.class));
        verify(timetableRepository).getReferenceById(any(Long.class));
    }

    @Test
    void create_validGroup_returnsSavedStudent() {
        when(timetableRepository.save(any(Timetable.class))).thenReturn(new Timetable());

        assertNotNull(timetableService.create(new Timetable()));
        verify(timetableRepository).save(any(Timetable.class));
    }

    @Test
    void create_invalidGroup_throwsException() {
        when(timetableRepository.save(any(Timetable.class))).thenThrow(IllegalArgumentException.class);

        EntityNotCreatedException exception = assertThrows(EntityNotCreatedException.class,
                () -> timetableService.create(new Timetable()));

        assertTrue(exception.getMessage().contains("was not created"));
        verify(timetableRepository).save(any(Timetable.class));
    }

    @Test
    void delete_validGroupId_returnsDeletedStudent() {
        when(timetableRepository.getReferenceById(any(Long.class))).thenReturn(new Timetable());

        assertNotNull(timetableService.deleteById(1L));
        verify(timetableRepository).delete(any(Timetable.class));
        verify(timetableRepository).getReferenceById(any(Long.class));
    }

    @Test
    void delete_invalidGroupId_throwsException() {
        when(timetableRepository.getReferenceById(any(Long.class))).thenThrow(IllegalArgumentException.class);

        EntityNotDeletedException exception = assertThrows(EntityNotDeletedException.class,
                () -> timetableService.deleteById(1L));

        assertTrue(exception.getMessage().contains("not deleted"));
        verify(timetableRepository).getReferenceById(any(Long.class));
    }
}