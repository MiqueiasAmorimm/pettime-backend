package com.pettime.model;

import lombok.Getter;

/**
 * Enum representing available user roles in the system.
 */
@Getter
public enum UserRole {

    CLIENT("Client"),
    PETSHOP("Pet Shop"),
    ADMIN("Admin");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }
}
