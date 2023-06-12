package com.example.universityschedule.service;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.entity.User;
import com.example.universityschedule.security.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDTO> getAll();

    UserDTO getById(Long id);

    UserDTO create(User user);

    UserDTO update(User user);

    UserDTO deleteById(Long id);

    List<UserDTO> createAll(List<User> users);

    void addGroup(Long userId, Long groupId);

    void deleteGroup(Long userId);

    void changeRole(Long id, Role role);

    void ban(Long id);
    List<UserDTO> getTutors();
}
