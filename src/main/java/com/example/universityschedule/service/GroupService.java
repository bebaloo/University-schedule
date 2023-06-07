package com.example.universityschedule.service;

import com.example.universityschedule.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> getAll();
    Group getById(Long id);
    Group create(Group group);
    Group update(Group group);
    Group deleteById(Long id);
    List<Group> createAll(List<Group> groups);
    void addCourse(Long groupId, Long courseId);
    List<Group> getByCourseId(Long id);
    void addStudent(Long groupId, Long studentId);
}
