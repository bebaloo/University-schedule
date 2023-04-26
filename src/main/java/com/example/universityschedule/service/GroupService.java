package com.example.universityschedule.service;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.GroupMapper;
import com.example.universityschedule.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public List<Group> getAll() {
        log.info("Getting all groups");
        return groupRepository.findAll();
    }

    public Group getById(Long id) {
        Optional<Group> course = groupRepository.findById(id);

        course.ifPresentOrElse(g -> log.info("Getting " + g),
                () -> log.info("Group with id: " + id + " not found"));

        return course.orElse(null);
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

            groupMapper.map(group, groupToUpdate);
            Group updatedGroup = groupRepository.save(groupToUpdate);

            log.info(groupToUpdate + " was updated to " + updatedGroup);

            return updatedGroup;
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
}
