package com.example.universityschedule.service;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();
    UserDTO getById(Long id);
    UserDTO create(User user);
    UserDTO update(User user);
    UserDTO deleteById(Long id);
    List<User> createAll(List<User> users);

    void addGroup(Long userId, Long groupId);

    void changeRole(Long id);

    void ban(Long id);
}
