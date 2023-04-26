package com.example.universityschedule.service;

import com.example.universityschedule.entity.Timetable;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.TimetableMapper;
import com.example.universityschedule.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TimetableService {
    private final TimetableRepository timetableRepository;
    private final TimetableMapper timetableMapper;

    public List<Timetable> getAll() {
        log.info("Getting all schedules");
        return timetableRepository.findAll();
    }

    public Timetable getById(Long id) {
        Optional<Timetable> timetable = timetableRepository.findById(id);

        timetable.ifPresentOrElse(g -> log.info("Getting " + g),
                () -> log.info("Schedule with id: " + id + " not found"));

        return timetable.orElse(null);
    }

    @Transactional
    public Timetable create(Timetable timetable) {
        try {
            Timetable createdTimetable = timetableRepository.save(timetable);
            log.info(timetable + " was created");

            return createdTimetable;
        } catch (RuntimeException e) {
            log.warn(timetable + " was not created");
            throw new EntityNotCreatedException(timetable + " was not created");
        }
    }

    @Transactional
    public Timetable update(Timetable timetable) {
        try {
            Timetable timetableToUpdate = timetableRepository.getReferenceById(timetable.getId());

            timetableMapper.map(timetable, timetableToUpdate);
            Timetable updatedTimetable = timetableRepository.save(timetableToUpdate);

            log.info(timetableToUpdate + " was updated to " + updatedTimetable);

            return updatedTimetable;
        } catch (RuntimeException e) {
            log.warn(timetable + " not updated");
            throw new EntityNotUpdatedException(timetable + " not updated");
        }
    }

    @Transactional
    public Timetable deleteById(Long id) {
        try {
            Timetable timetable = timetableRepository.getReferenceById(id);
            timetableRepository.delete(timetable);
            log.info(timetable + " was deleted");

            return timetable;
        } catch (RuntimeException e) {
            log.warn("Schedule with: " + id + " not deleted");
            throw new EntityNotDeletedException("Schedule with: " + id + " not deleted");
        }
    }
}
