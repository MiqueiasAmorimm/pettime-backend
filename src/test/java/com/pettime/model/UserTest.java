package com.pettime.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for User entity using UserRole enum.
 * (FR) Tests unitaires pour l'entité User utilisant l'énumération UserRole.
 */
class UserTest {

    @Test
    void testUserCreationWithEnumRole() {

        User user = User.builder()
                .name("Alice")
                .email("alice@test.com")
                .password("12345")
                .role(UserRole.CLIENT) // agora usa enum diretamente
                .build();


        assertThat(user.getName()).isEqualTo("Alice");
        assertThat(user.getEmail()).isEqualTo("alice@test.com");
        assertThat(user.getRole()).isEqualTo(UserRole.CLIENT);
    }

    @Test
    void testSettersAndGettersWithEnumRole() {

        User user = new User();
        user.setName("Bob");
        user.setEmail("bob@test.com");
        user.setPassword("12345");
        user.setRole(UserRole.ADMIN);


        assertThat(user.getName()).isEqualTo("Bob");
        assertThat(user.getEmail()).isEqualTo("bob@test.com");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void testRoleEnumValues() {

        assertThat(UserRole.values())
                .contains(UserRole.CLIENT, UserRole.PETSHOP, UserRole.ADMIN);
    }
}
