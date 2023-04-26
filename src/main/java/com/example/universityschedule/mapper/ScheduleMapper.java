package com.example.universityschedule.mapper;

import com.example.universityschedule.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    void map(Schedule scheduleUpdate, @MappingTarget Schedule schedule);
}
