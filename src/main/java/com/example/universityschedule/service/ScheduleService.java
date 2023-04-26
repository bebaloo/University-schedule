package com.example.universityschedule.service;

import com.example.universityschedule.entity.Schedule;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.ScheduleMapper;
import com.example.universityschedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public List<Schedule> getAll() {
        log.info("Getting all schedules");
        return scheduleRepository.findAll();
    }

    public Schedule getById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);

        schedule.ifPresentOrElse(g -> log.info("Getting " + g),
                () -> log.info("Schedule with id: " + id + " not found"));

        return schedule.orElse(null);
    }

    @Transactional
    public Schedule create(Schedule schedule) {
        try {
            Schedule createdSchedule = scheduleRepository.save(schedule);
            log.info(schedule + " was created");

            return createdSchedule;
        } catch (RuntimeException e) {
            log.warn(schedule + " was not created");
            throw new EntityNotCreatedException(schedule + " was not created");
        }
    }

    @Transactional
    public Schedule update(Schedule schedule) {
        try {
            Schedule scheduleToUpdate = scheduleRepository.getReferenceById(schedule.getId());

            scheduleMapper.map(schedule, scheduleToUpdate);
            Schedule updatedSchedule = scheduleRepository.save(scheduleToUpdate);

            log.info(scheduleToUpdate + " was updated to " + updatedSchedule);

            return updatedSchedule;
        } catch (RuntimeException e) {
            log.warn(schedule + " not updated");
            throw new EntityNotUpdatedException(schedule + " not updated");
        }
    }

    @Transactional
    public Schedule deleteById(Long id) {
        try {
            Schedule schedule = scheduleRepository.getReferenceById(id);
            scheduleRepository.delete(schedule);
            log.info(schedule + " was deleted");

            return schedule;
        } catch (RuntimeException e) {
            log.warn("Schedule with: " + id + " not deleted");
            throw new EntityNotDeletedException("Schedule with: " + id + " not deleted");
        }
    }
}
