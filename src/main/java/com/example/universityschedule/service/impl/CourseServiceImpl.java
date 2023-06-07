package com.example.universityschedule.service.impl;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotFoundException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.CourseMapper;
import com.example.universityschedule.repository.CourseRepository;
import com.example.universityschedule.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<Course> getAll() {
        log.info("Getting all courses");
        return courseRepository.findAll();
    }

    public Course getById(Long id) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            log.info("Getting {}", course);

            return course;
        } catch (RuntimeException e) {
            log.info("Course with id: {} not found", id);
            throw new EntityNotFoundException("Course with id: " + id + " not found");
        }
    }

    @Transactional
    public Course create(Course course) {
        try {
            Course createdCourse = courseRepository.save(course);
            log.info("{} was created", course);

            return createdCourse;
        } catch (RuntimeException e) {
            log.warn("{} was not created", course);
            throw new EntityNotCreatedException(course + " was not created");
        }
    }

    @Transactional
    public Course update(Course course) {
        try {
            Course courseToUpdate = courseRepository.getReferenceById(course.getId());

            courseMapper.updateCourse(course, courseToUpdate);
            courseRepository.save(courseToUpdate);

            log.info("{} was updated", courseToUpdate);

            return courseToUpdate;
        } catch (RuntimeException e) {
            log.warn("{} not updated", course);
            throw new EntityNotUpdatedException(course + " not updated");
        }
    }

    @Transactional
    public Course deleteById(Long id) {
        try {
            Course course = courseRepository.getReferenceById(id);
            courseRepository.delete(course);
            log.info("{} was deleted", course);

            return course;
        } catch (RuntimeException e) {
            log.warn("Course with: {} not deleted", id);
            throw new EntityNotDeletedException("Course with: " + id + " not deleted");
        }
    }

    @Transactional
    public List<Course> createAll(List<Course> courses) {
        try {
            List<Course> createdCourses = courseRepository.saveAll(courses);
            log.info("{} was created", courses);

            return createdCourses;
        } catch (RuntimeException e) {
            log.warn("{} were not created", courses);
            throw new EntityNotCreatedException(courses + " were not created");
        }
    }
}
