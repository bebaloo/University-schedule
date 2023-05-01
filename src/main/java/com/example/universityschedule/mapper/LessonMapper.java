package com.example.universityschedule.mapper;

import com.example.universityschedule.entity.Lesson;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLesson(Lesson lesson, @MappingTarget Lesson lessonToUpdate);
}
