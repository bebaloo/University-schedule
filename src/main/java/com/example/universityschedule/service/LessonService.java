package com.example.universityschedule.service;

import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.entity.User;

import java.util.List;

public interface LessonService {
    List<Lesson> getAll();
    Lesson getById(Long id);
    Lesson create(Lesson lesson);
    Lesson update(Lesson lesson);
    Lesson deleteById(Long id);
    List<Lesson> createAll(List<Lesson> lessons);
    void addGroup(Long lessonId, Long groupId);
    List<Lesson> getByUser(User user);
}
