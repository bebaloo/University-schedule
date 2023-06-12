package com.example.universityschedule.repository;

import com.example.universityschedule.entity.User;
import com.example.universityschedule.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByGroup_Id(Long groupId);
    List<User> findByRole(Role role);
}