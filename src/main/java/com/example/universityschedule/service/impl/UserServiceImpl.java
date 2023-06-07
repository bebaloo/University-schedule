package com.example.universityschedule.service.impl;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.entity.Group;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.exception.EntityNotCreatedException;
import com.example.universityschedule.exception.EntityNotDeletedException;
import com.example.universityschedule.exception.EntityNotFoundException;
import com.example.universityschedule.exception.EntityNotUpdatedException;
import com.example.universityschedule.mapper.UserMapper;
import com.example.universityschedule.repository.GroupRepository;
import com.example.universityschedule.repository.UserRepository;
import com.example.universityschedule.security.Role;
import com.example.universityschedule.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAll() {
        log.info("Getting all users");
        return userMapper
                .toDto(userRepository.findAll())
                .stream()
                .sorted(Comparator.comparing(UserDTO::id))
                .toList();
    }

    public UserDTO getById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            log.info("Getting user: {}", user);

            return userMapper.toDto(user);
        } catch (RuntimeException e) {
            log.info("User with id: {} not found", id);
            throw new EntityNotFoundException("User with id: " + id + " not found");
        }
    }

    @Transactional
    public UserDTO create(User user) {
        try {
            User createdUser = userRepository.save(user);
            log.info("User: {} was created", user);

            return userMapper.toDto(createdUser);
        } catch (RuntimeException e) {
            log.warn("User: {} was not created", user);
            throw new EntityNotCreatedException("User: " + user + " was not created");
        }
    }

    @Transactional
    public UserDTO update(User user) {
        try {
            User userToUpdate = userRepository.getReferenceById(user.getId());

            userRepository.save(mapUpdate(userToUpdate, user));

            log.info("User: {} was updated", userToUpdate);

            return userMapper.toDto(userToUpdate);
        } catch (RuntimeException e) {
            log.warn("User: {} not updated", user);
            throw new EntityNotUpdatedException("User: " + user + " not updated");
        }
    }

    @Transactional
    public UserDTO deleteById(Long id) {
        try {
            User user = userRepository.getReferenceById(id);
            userRepository.delete(user);
            log.info("User: {} was deleted", user);

            return userMapper.toDto(user);
        } catch (RuntimeException e) {
            log.warn("User with id: {} not deleted", id);
            throw new EntityNotDeletedException("User with: " + id + " not deleted");
        }
    }

    @Transactional
    public List<UserDTO> createAll(List<User> users) {
        try {
            List<User> createdUsers = userRepository.saveAll(users);
            log.info("Users: {} were created", users);

            return userMapper.toDto(createdUsers);
        } catch (RuntimeException e) {
            log.warn("Users: {} were not created", users);
            throw new EntityNotCreatedException("Users: " + users + " were not created");
        }
    }

    @Transactional
    public void addGroup(Long studentId, Long groupId) {
        try {
            Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
            User student = userRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);

            student.setGroup(group);

            userRepository.save(student);
            log.info("Student with id: {} was added to group with id: {}", studentId, groupId);
        } catch (RuntimeException e) {
            log.info("Student with id: {} was not added to group with id: {}", studentId, groupId);
            throw new EntityNotUpdatedException("Student with id: " + studentId + " was not added to group with id: " + groupId);
        }
    }

    @Override
    public void deleteGroup(Long userId) {
        try {
            User student = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

            student.setGroup(null);

            userRepository.save(student);
            log.info("Student with id: {} was deleted from group", userId);
        } catch (RuntimeException e) {
            log.info("Student with id: {} was not deleted from group", userId);
            throw new EntityNotUpdatedException("Student with id: " + userId + " was not deleted from group");
        }
    }

    @Override
    public void changeRole(Long id, Role role) {
        User user = userRepository.getReferenceById(id);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with email: " + username + " not found"));
    }

    @Override
    public void ban(Long id) {
        User user = userRepository.getReferenceById(id);
        boolean isActive = user.isActive();

        user.setActive(!isActive);

        userRepository.save(user);
    }

    private User mapUpdate(User userToUpdate, User user) {
        if (user.getFirstname() != null) {
            userToUpdate.setFirstname(user.getFirstname());
        }
        if (user.getLastname() != null) {
            userToUpdate.setLastname(user.getLastname());
        }
        if (user.getRole() != null) {
            userToUpdate.setRole(user.getRole());
        }
        if (user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
        }
        if (user.getGroup() != null) {
            userToUpdate.setGroup(user.getGroup());
        }
        return userToUpdate;
    }
}
