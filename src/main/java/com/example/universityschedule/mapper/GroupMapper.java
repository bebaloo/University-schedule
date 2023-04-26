package com.example.universityschedule.mapper;

import com.electronwill.nightconfig.core.conversion.ForceBreakdown;
import com.example.universityschedule.entity.Group;
import lombok.Getter;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void map(Group groupUpdate, @MappingTarget Group group);
}
