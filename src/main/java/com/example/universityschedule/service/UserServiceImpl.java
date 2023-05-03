package com.example.universityschedule.service;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotFoundException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.UserMapper;
import com.example.universityschedule.repository.GroupRepository;
import com.example.universityschedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    public List<User> getAll() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    public User getById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            log.info("Getting " + user);

            return user;
        } catch (RuntimeException e) {
            log.info("User with id: " + id + " not found");
            throw new EntityNotFoundException("User with id: " + id + " not found");
        }
    }

    @Transactional
    public User create(User user) {
        try {
            User createdUser = userRepository.save(user);
            log.info(user + " was created");

            return createdUser;
        } catch (RuntimeException e) {
            log.warn(user + " was not created");
            throw new EntityNotCreatedException(user + " was not created");
        }
    }

    @Transactional
    public User update(User user) {
        try {
            User userToUpdate = userRepository.getReferenceById(user.getId());

            userMapper.updateUser(user, userToUpdate);
            userRepository.save(userToUpdate);

            log.info(userToUpdate + " was updated");

            return userToUpdate;
        } catch (RuntimeException e) {
            log.warn(user + " not updated");
            throw new EntityNotUpdatedException(user + " not updated");
        }
    }

    @Transactional
    public User deleteById(Long id) {
        try {
            User user = userRepository.getReferenceById(id);
            userRepository.delete(user);
            log.info(user + " was deleted");

            return user;
        } catch (RuntimeException e) {
            log.warn("User with: " + id + " not deleted");
            throw new EntityNotDeletedException("User with: " + id + " not deleted");
        }
    }

    @Transactional
    public List<User> createAll(List<User> users) {
        try {
            List<User> createdUsers = userRepository.saveAll(users);
            log.info(users + " were created");

            return createdUsers;
        } catch (RuntimeException e) {
            log.warn(users + " were not created");
            throw new EntityNotCreatedException(users + " were not created");
        }
    }

    @Transactional
    public void addGroup(Long studentId, Long groupId) {
        try {
            Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
            User student = userRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);

            student.setGroup(group);

            userRepository.save(student);
            log.info("Student with id: " + studentId + " was added to group with id: " + groupId);
        } catch (RuntimeException e) {
            log.info("Student with id: " + studentId + " was not added to group with id: " + groupId);
            throw new EntityNotUpdatedException("Student with id: " + studentId + " was added to group with id: " + groupId);
        }
    }
}
