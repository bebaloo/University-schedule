package com.example.universityschedule.service;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();
    UserDTO getById(Long id);
    User create(User user);
    User update(User user);
    User deleteById(Long id);
    List<User> createAll(List<User> users);

    void addGroup(Long userId, Long groupId);

    void changeRole(Long id);

    void ban(Long id);
}
