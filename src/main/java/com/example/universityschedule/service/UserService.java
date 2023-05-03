package com.example.universityschedule.service;

import com.example.universityschedule.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User create(User user);
    User update(User user);
    User deleteById(Long id);
    List<User> createAll(List<User> users);

    void addGroup(Long userId, Long groupId);
}
