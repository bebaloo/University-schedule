package com.example.universityschedule.service.impl;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotFoundException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.repository.GroupRepository;
import com.example.universityschedule.repository.LessonRepository;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;

    public List<Lesson> getAll() {
        log.info("Getting all lessons");
        return lessonRepository.findAll();
    }

    public Lesson getById(Long id) {
        try {
            Lesson lesson = lessonRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            log.info("Getting {}", lesson);

            return lesson;
        } catch (RuntimeException e) {
            log.info("Lesson with id: {} not found", id);
            throw new EntityNotFoundException("Lesson with id: " + id + " not found");
        }
    }

    @Transactional
    public Lesson create(Lesson lesson) {
        try {
            Lesson createdLesson = lessonRepository.save(lesson);
            log.info("{} was created", lesson);

            return createdLesson;
        } catch (RuntimeException e) {
            log.warn("{} was not created", lesson);
            throw new EntityNotCreatedException(lesson + " was not created");
        }
    }

    @Transactional
    public Lesson update(Lesson lesson) {
        try {
            Lesson lessonToUpdate = lessonRepository.findById(lesson.getId()).orElseThrow(EntityNotFoundException::new);


            lessonRepository.save(mapUpdate(lessonToUpdate, lesson));

            log.info("{} was updated", lessonToUpdate);

            return lessonToUpdate;
        } catch (RuntimeException e) {
            log.warn("{} not updated", lesson);
            throw new EntityNotUpdatedException(lesson + " not updated");
        }
    }

    @Transactional
    public Lesson deleteById(Long id) {
        try {
            Lesson lesson = lessonRepository.getReferenceById(id);
            lessonRepository.delete(lesson);
            log.info("{} was deleted", lesson);

            return lesson;
        } catch (RuntimeException e) {
            log.warn("Lesson with: {} not deleted", id);
            throw new EntityNotDeletedException("Lesson with: " + id + " not deleted");
        }
    }

    @Transactional
    public List<Lesson> createAll(List<Lesson> lessons) {
        try {
            List<Lesson> createdLessons = lessonRepository.saveAll(lessons);
            log.info("{} were created", lessons);

            return createdLessons;
        } catch (RuntimeException e) {
            log.warn("{} were not created", lessons);
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
            log.info("Group with id: {} was added to lesson with id: {}", groupId, lessonId);
        } catch (RuntimeException e) {
            log.info("Group with id: {} was not added to lesson with id: {}", groupId, lessonId);
            throw new EntityNotUpdatedException("Group with id: " + groupId + " was not added to lesson with id: " + lessonId);
        }
    }

    @Override
    public List<Lesson> getByUser(User user) {
        List<Lesson> lessons;

        if (user.getRole() == Role.ADMIN) {
            lessons = getAll();
        } else if (user.getRole() == Role.TUTOR) {
            lessons = lessonRepository.findByTutor(user);
        } else {
            Group group = groupRepository.findByStudents(user);
            lessons = lessonRepository.findByGroup(group);
        }

        return lessons.stream()
                .sorted(Comparator
                        .comparing(Lesson::getDayOfWeek)
                        .thenComparing(Lesson::getNumber))
                .toList();
    }

    private Lesson mapUpdate(Lesson lessonToUpdate, Lesson updatedLesson) {
        if (updatedLesson.getName() != null) {
            lessonToUpdate.setName(updatedLesson.getName());
        }
        if (updatedLesson.getClassroom() != null) {
            lessonToUpdate.setClassroom(updatedLesson.getClassroom());
        }
        if (updatedLesson.getNumber() != null) {
            lessonToUpdate.setNumber(updatedLesson.getNumber());
        }
        if (updatedLesson.getDayOfWeek() != null) {
            lessonToUpdate.setDayOfWeek(updatedLesson.getDayOfWeek());
        }
        if (updatedLesson.getTutor() != null) {
            lessonToUpdate.setTutor(updatedLesson.getTutor());
        }
        if (updatedLesson.getGroup() != null) {
            lessonToUpdate.setGroup(updatedLesson.getGroup());
        }
        return lessonToUpdate;
    }
}