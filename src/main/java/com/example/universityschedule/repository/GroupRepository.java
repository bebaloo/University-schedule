package com.example.universityschedule.repository;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByCourse_Id(Long id);

    Group findByStudents(User student);
}