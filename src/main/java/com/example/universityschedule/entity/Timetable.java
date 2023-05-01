package com.example.universityschedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "timetables")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Timetable(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Timetable timetable = (Timetable) o;
        return id != null && Objects.equals(id, timetable.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
