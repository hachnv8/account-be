package com.hacheery.accountbe.security.mapper;


import com.hacheery.accountbe.security.dto.UserDto;
import com.hacheery.accountbe.security.entity.User;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
