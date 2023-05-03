package com.example.universityschedule.service;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.entity.User;

import java.util.List;

public interface DataGeneratorService {
    Integer USERS_NUMBER = 100;
    Integer COURSES_NUMBER = 5;
    Integer GROUPS_NUMBER = 10;
    Integer LESSONS_NUMBER = 20;

    void fillDB();
    void addGroupsToCourses();
    void addStudentsToGroups();
    void addGroupsToLessons();
    List<Course> generateCourses();
    List<Group> generateGroups();
    List<Lesson> generateLessons();
    List<User> generateUsers();
}
