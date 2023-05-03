package com.example.universityschedule.service;

import com.example.universityschedule.entity.*;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.security.Status;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataGeneratorService {
    private static final Integer USERS_NUMBER = 100;
    private static final Integer COURSES_NUMBER = 5;
    private static final Integer GROUPS_NUMBER = 10;
    private static final Integer LESSONS_NUMBER = 20;

    private final CourseService courseService;
    private final GroupService groupService;
    private final LessonService lessonService;
    private final UserService userService;
    private final Faker faker;
    private final FakeValuesService fakeService;

    public void fillDB() {
        if (userService.getAll().isEmpty()) {
            courseService.createAll(generateCourses());
            userService.createAll(generateUsers());
            groupService.createAll(generateGroups());
            lessonService.createAll(generateLessons());

            addGroupsToCourses();
            addStudentsToGroups();
            addGroupsToLessons();
        }
    }

    private void addGroupsToCourses() {
        List<Group> groups = groupService.getAll();
        groups.forEach(group -> groupService.addCourse(group.getId(), (long) faker.number().numberBetween(1, 6)));
    }

    private void addStudentsToGroups() {
        List<User> students = userService.getAll();
        students.forEach(student -> userService.addGroup(student.getId(), (long) faker.number().numberBetween(1, 11)));
    }

    private void addGroupsToLessons() {
        List<Lesson> lessons = lessonService.getAll();
        lessons.forEach(lesson -> lessonService.addGroup(lesson.getId(), (long) faker.number().numberBetween(1, 11)));
    }

    private List<Course> generateCourses() {
        List<Course> courses = new ArrayList<>();

        for (int i = 0; i <= COURSES_NUMBER; i++) {
            Course course = new Course();

            course.setName(faker.job().title());

            courses.add(course);
        }
        return courses;
    }

    private List<Group> generateGroups() {
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i <= GROUPS_NUMBER; i++) {
            Group group = new Group();

            group.setName(fakeService.bothify("??-##"));

            groups.add(group);
        }
        return groups;
    }

    private List<Lesson> generateLessons() {
        List<Lesson> lessons = new ArrayList<>();

        for (int i = 0; i <= LESSONS_NUMBER; i++) {
            Lesson lesson = new Lesson();

            lesson.setName(faker.programmingLanguage().name());
            lesson.setClassroom(faker.number().numberBetween(100, 399) + faker.letterify("?"));
            lesson.setDayOfWeek(DayOfWeek.MONDAY);

            lessons.add(lesson);
        }
        return lessons;
    }

    private List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= USERS_NUMBER; i++) {
            User user = new User();

            user.setFirstname(faker.name().firstName());
            user.setLastname(faker.name().lastName());
            user.setDepartment(faker.company().profession());
            user.setFaculty(faker.job().title());
            user.setEmail(faker.name().username() + faker.number().numberBetween(1970, 2023) + "@gmail.com");
            user.setRole(Role.STUDENT);
            user.setStatus(Status.ACTIVE);

            users.add(user);
        }
        return users;
    }
}
