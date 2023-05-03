package com.example.universityschedule.service;

import com.example.universityschedule.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAll();
    Course getById(Long id);
    Course create(Course course);
    Course update(Course course);
    Course deleteById(Long id);
    List<Course> createAll(List<Course> courses);
}
