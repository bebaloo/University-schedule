package com.example.universityschedule.mapper;

import com.example.universityschedule.entity.Timetable;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TimetableMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTimetable(Timetable timetableUpdate, @MappingTarget Timetable timetable);
}
