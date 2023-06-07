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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
            log.info("Getting {}", group);

            return group;
        } catch (RuntimeException e) {
            log.info("Group with id: {} not found", id);
            throw new EntityNotFoundException("Group with id: " + id + " not found");
        }
    }

    @Transactional
    public Group create(Group group) {
        try {
            Group createdCourse = groupRepository.save(group);
            log.info("{} was created", group);

            return createdCourse;
        } catch (RuntimeException e) {
            log.warn("{} was not created", group);
            throw new EntityNotCreatedException(group + " was not created");
        }
    }

    @Transactional
    public Group update(Group group) {
        try {
            Group groupToUpdate = groupRepository.getReferenceById(group.getId());

            groupMapper.updateGroup(group, groupToUpdate);
            groupRepository.save(groupToUpdate);

            log.info("{} was updated", groupToUpdate);

            return groupToUpdate;
        } catch (RuntimeException e) {
            log.warn("{} not updated", group);
            throw new EntityNotUpdatedException(group + " not updated");
        }
    }

    @Transactional
    public Group deleteById(Long id) {
        try {
            Group group = groupRepository.getReferenceById(id);
            User user = userRepository.findByGroup_Id(id);
            user.setGroup(null);
            groupRepository.delete(group);
            log.info("{} was deleted", group);

            return group;
        } catch (RuntimeException e) {
            log.warn("Group with: {} not deleted", id);
            throw new EntityNotDeletedException("Group with: " + id + " not deleted");
        }
    }

    @Transactional
    public List<Group> createAll(List<Group> groups) {
        try {
            List<Group> createdCourses = groupRepository.saveAll(groups);
            log.info("{} were created", groups);

            return createdCourses;
        } catch (RuntimeException e) {
            log.warn("{} were not created", groups);
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
            log.info("Course with id: {} was added to group with id: {}", courseId, groupId);
        } catch (RuntimeException e) {
            log.warn("Course with id: {} was not added to group with id: {}", courseId, groupId);
            throw new EntityNotUpdatedException("Course with id: " + courseId + " was not added to group with id: " + groupId);
        }
    }

    public List<Group> getByCourseId(Long courseId) {
        log.info("Getting groups by course id: {}", courseId);
        return groupRepository.findByCourse_Id(courseId);
    }

    @Override
    public void addStudent(Long groupId, Long userId) {
        try {
            Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
            User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

            user.setGroup(group);

            userRepository.save(user);
            log.warn("User with id: {} was added to group with id: {}", userId, groupId);
        } catch (RuntimeException e) {
            log.warn("User with id: {} was not added to group with id: {}", userId, groupId);
            throw new EntityNotUpdatedException("User with id: " + userId + " was not added to group with id: " + groupId);
        }
    }
}
