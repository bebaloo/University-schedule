package com.example.universityschedule.mapper;

import com.example.universityschedule.entity.Timetable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TimetableMapper {
    void map(Timetable timetableUpdate, @MappingTarget Timetable timetable);
}
