package com.tanish.jwtAuthenticationProject.mapper;

import com.tanish.jwtAuthenticationProject.dto.UserDto;
import com.tanish.jwtAuthenticationProject.entity.User;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapper {
    public UserDto mapUserToDtoBasic(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;

    }
}
