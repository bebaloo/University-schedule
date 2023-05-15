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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAll() {
        log.info("Getting all users");
        return userMapper.toDto(userRepository.findAll())
                .stream()
                .sorted(Comparator.comparing(UserDTO::id))
                .toList();
    }

    public UserDTO getById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            log.info("Getting " + user);

            return userMapper.toDto(user);
        } catch (RuntimeException e) {
            log.info("User with id: " + id + " not found");
            throw new EntityNotFoundException("User with id: " + id + " not found");
        }
    }

    @Transactional
    public UserDTO create(User user) {
        try {
            User createdUser = userRepository.save(user);
            log.info(user + " was created");

            return userMapper.toDto(createdUser);
        } catch (RuntimeException e) {
            log.warn(user + " was not created");
            throw new EntityNotCreatedException(user + " was not created");
        }
    }

    @Transactional
    public UserDTO update(User user) {
        try {
            User userToUpdate = userRepository.getReferenceById(user.getId());

            userRepository.save(mapUpdate(userToUpdate, user));

            log.info(userToUpdate + " was updated");

            return userMapper.toDto(userToUpdate);
        } catch (RuntimeException e) {
            log.warn(user + " not updated");
            throw new EntityNotUpdatedException(user + " not updated");
        }
    }

    @Transactional
    public UserDTO deleteById(Long id) {
        try {
            User user = userRepository.getReferenceById(id);
            userRepository.delete(user);
            log.info(user + " was deleted");

            return userMapper.toDto(user);
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

    @Override
    public void changeRole(Long id) {
        User user = userRepository.getReferenceById(id);
        Role role = user.getRole();
        if (role == Role.STUDENT) {
            user.setRole(Role.TUTOR);
        } else if (role == Role.TUTOR) {
            user.setRole(Role.ADMIN);
        } else if (role == Role.ADMIN) {
            user.setRole(Role.STUDENT);
        }

        userRepository.save(user);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()
                -> new UsernameNotFoundException("User with email: " + username + " not found"));
    }
}
