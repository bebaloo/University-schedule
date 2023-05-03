package com.example.universityschedule.entity;

import com.example.universityschedule.security.Role;
import com.example.universityschedule.security.Status;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "users_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String faculty;
    private String department;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String firstname, String lastname, String email, String faculty, String department, Role role, Status status, Group group) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.faculty = faculty;
        this.department = department;
        this.role = role;
        this.status = status;
        this.group = group;
    }
}