package com.example.universityschedule.mapper;

import com.example.universityschedule.dto.UserDTO;
import com.example.universityschedule.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromDto(UserDTO userDTO);
    List<User> fromDto(List<UserDTO> userDTOS);

    UserDTO toDto(User user);
    List<UserDTO> toDto(List<User> users);
}
