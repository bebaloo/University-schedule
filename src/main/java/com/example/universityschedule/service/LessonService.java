package com.example.universityschedule.service;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotFoundException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.LessonMapper;
import com.example.universityschedule.repository.GroupRepository;
import com.example.universityschedule.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class LessonService {
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final LessonMapper lessonMapper;

    public List<Lesson> getAll() {
        log.info("Getting all lessons");
        return lessonRepository.findAll();
    }

    public Lesson getById(Long id) {
        Optional<Lesson> course = lessonRepository.findById(id);

        course.ifPresentOrElse(g -> log.info("Getting " + g),
                () -> log.info("Lesson with id: " + id + " not found"));

        return course.orElse(null);
    }

    @Transactional
    public Lesson create(Lesson lesson) {
        try {
            Lesson createdLesson = lessonRepository.save(lesson);
            log.info(lesson + " was created");

            return createdLesson;
        } catch (RuntimeException e) {
            log.warn(lesson + " was not created");
            throw new EntityNotCreatedException(lesson + " was not created");
        }
    }

    @Transactional
    public Lesson update(Lesson lesson) {
        try {
            Lesson lessonToUpdate = lessonRepository.getReferenceById(lesson.getId());

            lessonMapper.updateLesson(lesson, lessonToUpdate);
            lessonRepository.save(lessonToUpdate);

            log.info(lessonToUpdate + " was updated");

            return lessonToUpdate;
        } catch (RuntimeException e) {
            log.warn(lesson + " not updated");
            throw new EntityNotUpdatedException(lesson + " not updated");
        }
    }

    @Transactional
    public Lesson deleteById(Long id) {
        try {
            Lesson lesson = lessonRepository.getReferenceById(id);
            lessonRepository.delete(lesson);
            log.info(lesson + " was deleted");

            return lesson;
        } catch (RuntimeException e) {
            log.warn("Lesson with: " + id + " not deleted");
            throw new EntityNotDeletedException("Lesson with: " + id + " not deleted");
        }
    }

    @Transactional
    public List<Lesson> createAll(List<Lesson> lessons) {
        try {
            List<Lesson> createdLessons = lessonRepository.saveAll(lessons);
            log.info(lessons + " were created");

            return createdLessons;
        } catch (RuntimeException e) {
            log.warn(lessons + " were not created");
            throw new EntityNotCreatedException(lessons + " were not created");
        }
    }

    @Transactional
    public void addGroup(Long lessonId, Long groupId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(EntityNotFoundException::new);
            Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);

            lesson.setGroup(group);

            lessonRepository.save(lesson);
            log.info("Group with id: " + groupId + " was added to lesson with id: " + lessonId);
        } catch (RuntimeException e) {
            log.info("Group with id: " + groupId + " was not added to lesson with id: " + lessonId);
            throw new EntityNotUpdatedException("Group with id: " + groupId + " was not added to lesson with id: " + lessonId);
        }
    }
}
