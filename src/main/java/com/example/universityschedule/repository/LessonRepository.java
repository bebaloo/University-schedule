package com.example.universityschedule.repository;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.Lesson;
import com.example.universityschedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByTutor(User tutor);
    List<Lesson> findByGroup(Group group);
}