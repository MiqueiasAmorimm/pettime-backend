package com.pettime.service;

import com.pettime.dto.UserRequestDto;
import com.pettime.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponseDto> findAll();

    Optional<UserResponseDto> findById(Long id);

    Optional<UserResponseDto> findByEmail(String email);

    UserResponseDto createUser(UserRequestDto dto);

    Optional<UserResponseDto> updateUser(Long id, UserRequestDto dto);

    boolean deleteUser(Long id);
}