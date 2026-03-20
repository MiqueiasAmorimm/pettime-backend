package com.pettime.mapper;

import com.pettime.dto.UserRequestDto;
import com.pettime.dto.UserResponseDto;
import com.pettime.model.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static UserResponseDto toResponseDto(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}