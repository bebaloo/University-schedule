package com.example.universityschedule.service.impl;

import com.example.universityschedule.entity.Course;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotFoundException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.GroupMapper;
import com.example.universityschedule.repository.CourseRepository;
import com.example.universityschedule.repository.GroupRepository;
import com.example.universityschedule.repository.UserRepository;
import com.example.universityschedule.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;

    public List<Group> getAll() {
        log.info("Getting all groups");
        return groupRepository.findAll();
    }

    public Group getById(Long id) {
        try {
            Group group = groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            log.info("Getting " + group);

            return group;
        } catch (RuntimeException e) {
            log.info("Group with id: " + id + " not found");
            throw new EntityNotFoundException("Group with id: " + id + " not found");
        }
    }

    @Transactional
    public Group create(Group group) {
        try {
            Group createdCourse = groupRepository.save(group);
            log.info(group + " was created");

            return createdCourse;
        } catch (RuntimeException e) {
            log.warn(group + " was not created");
            throw new EntityNotCreatedException(group + " was not created");
        }
    }

    @Transactional
    public Group update(Group group) {
        try {
            Group groupToUpdate = groupRepository.getReferenceById(group.getId());

            groupMapper.updateGroup(group, groupToUpdate);
            groupRepository.save(groupToUpdate);

            log.info(groupToUpdate + " was updated");

            return groupToUpdate;
        } catch (RuntimeException e) {
            log.warn(group + " not updated");
            throw new EntityNotUpdatedException(group + " not updated");
        }
    }

    @Transactional
    public Group deleteById(Long id) {
        try {
            Group group = groupRepository.getReferenceById(id);
            groupRepository.delete(group);
            log.info(group + " was deleted");

            return group;
        } catch (RuntimeException e) {
            log.warn("Group with: " + id + " not deleted");
            throw new EntityNotDeletedException("Group with: " + id + " not deleted");
        }
    }

    @Transactional
    public List<Group> createAll(List<Group> groups) {
        try {
            List<Group> createdCourses = groupRepository.saveAll(groups);
            log.info(groups + " were created");

            return createdCourses;
        } catch (RuntimeException e) {
            log.warn(groups + " were not created");
            throw new EntityNotCreatedException(groups + " were not created");
        }
    }

    @Transactional
    public void addCourse(Long groupId, Long courseId) {
        try {
            Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
            Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);

            group.setCourse(course);

            groupRepository.save(group);
            log.info("Course with id: " + courseId + " was added to group with id: " + groupId);
        } catch (RuntimeException e) {
            log.warn("Course with id: " + courseId + " was not added to group with id: " + groupId);
            throw new EntityNotUpdatedException("Course with id: " + courseId + " was not added to group with id: " + groupId);
        }
    }

    public List<Group> getByCourseId(Long courseId) {
        log.info("Getting groups by course id: " + courseId);
        return groupRepository.findByCourse_Id(courseId);
    }

    @Override
    public void addStudent(Long groupId, Long userId) {
        try {
            Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
            User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

            user.setGroup(group);

            userRepository.save(user);
            log.warn("User with id: " + userId + " was not added to group with id: " + groupId);
        } catch (RuntimeException e) {
            log.warn("User with id: " + userId + " was not added to group with id: " + groupId);
            throw new EntityNotUpdatedException("User with id: " + userId + " was not added to group with id: " + groupId);
        }
    }
}
