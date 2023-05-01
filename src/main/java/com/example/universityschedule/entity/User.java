package com.example.universityschedule.entity;

import com.example.universityschedule.Role;
import com.example.universityschedule.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String faculty;
    private String department;
    private Role role;
    private Status status;
    @OneToOne(mappedBy = "user")
    private Timetable timetable;

    public User(Long id) {
        this.id = id;
    }
}