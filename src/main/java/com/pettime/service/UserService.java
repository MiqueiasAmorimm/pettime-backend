package com.pettime.service;

import com.pettime.dto.UserDto;
import com.pettime.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User createUser(UserDto userDto);

    Optional<User> updateUser(Long id, UserDto userDto);

    boolean deleteUser(Long id);
}
