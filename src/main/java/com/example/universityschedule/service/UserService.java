package com.example.universityschedule.service;

import com.example.universityschedule.entity.User;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.UserMapper;
import com.example.universityschedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAll() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);

        user.ifPresentOrElse(g -> log.info("Getting " + g),
                () -> log.info("User with id: " + id + " not found"));

        return user.orElse(null);
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

            userMapper.map(user, userToUpdate);
            User updatedUser = userRepository.save(userToUpdate);

            log.info(userToUpdate + " was updated to " + updatedUser);

            return updatedUser;
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
}
