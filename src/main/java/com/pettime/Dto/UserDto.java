package com.pettime.Dto;

import com.pettime.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String email;
    private User.UserRole role;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .role(this.role)
                .build();
    }
}
