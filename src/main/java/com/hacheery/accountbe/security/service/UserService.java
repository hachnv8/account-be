package com.hacheery.accountbe.security.service;

import com.hacheery.accountbe.security.dto.UserDto;
import com.hacheery.accountbe.security.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getUsers();
    User getUserById(Long userId);
    User updateUser(Long userId, User user);
    UserDto findByUsername(String username);
}
