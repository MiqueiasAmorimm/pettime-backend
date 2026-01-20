package com.pettime.service;

import com.pettime.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);

    Optional<UserDto> findByEmail(String email);

    UserDto createUser(UserDto dto);

    Optional<UserDto> updateUser(Long id, UserDto dto);

    boolean deleteUser(Long id);
}
