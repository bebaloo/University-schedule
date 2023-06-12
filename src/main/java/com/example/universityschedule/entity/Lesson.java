package com.example.universityschedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.DayOfWeek;
import java.util.Objects;

@Entity
@Table(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_generator")
    @SequenceGenerator(name = "lesson_generator", sequenceName = "lessons_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String classroom;
    private Byte number;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private User tutor;
    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Lesson(String name) {
        this.name = name;
    }

    public Lesson(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Lesson(Long id, String name, Group group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Lesson lesson = (Lesson) o;
        return id != null && Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
