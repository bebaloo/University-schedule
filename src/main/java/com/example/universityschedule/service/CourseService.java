package com.example.universityschedule.service;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.CourseMapper;
import com.example.universityschedule.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<Course> getAll() {
        log.info("Getting all courses");
        return courseRepository.findAll();
    }

    public Course getById(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        course.ifPresentOrElse(g -> log.info("Getting " + g),
                () -> log.info("Course with id: " + id + " not found"));

        return course.orElse(null);
    }

    @Transactional
    public Course create(Course course) {
        try {
            Course createdCourse = courseRepository.save(course);
            log.info(course + " was created");

            return createdCourse;
        } catch (RuntimeException e) {
            log.warn(course + " was not created");
            throw new EntityNotCreatedException(course + " was not created");
        }
    }

    @Transactional
    public Course update(Course course) {
        try {
            Course courseToUpdate = courseRepository.getReferenceById(course.getId());

            courseMapper.updateGroup(course, courseToUpdate);
            courseRepository.save(courseToUpdate);

            log.info(courseToUpdate + " was updated");

            return courseToUpdate;
        } catch (RuntimeException e) {
            log.warn(course + " not updated");
            throw new EntityNotUpdatedException(course + " not updated");
        }
    }

    @Transactional
    public Course deleteById(Long id) {
        try {
            Course course = courseRepository.getReferenceById(id);
            courseRepository.delete(course);
            log.info(course + " was deleted");

            return course;
        } catch (RuntimeException e) {
            log.warn("Course with: " + id + " not deleted");
            throw new EntityNotDeletedException("Course with: " + id + " not deleted");
        }
    }
}
