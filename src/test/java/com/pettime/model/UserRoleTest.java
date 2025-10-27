package com.pettime.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for UserRole enum.
 * (FR) Tests unitaires pour l'énumération UserRole.
 */
class UserRoleTest {

    @Test
    void testEnumValues() {
        assertThat(UserRole.CLIENT.getRoleName()).isEqualTo("Client");
        assertThat(UserRole.PETSHOP.getRoleName()).isEqualTo("Pet Shop");
        assertThat(UserRole.ADMIN.getRoleName()).isEqualTo("Admin");
    }

    @Test
    void testEnumConsistency() {
        for (UserRole role : UserRole.values()) {
            assertThat(role.name()).isIn("CLIENT", "PETSHOP", "ADMIN");
        }
    }

    @Test
    void testInvalidRoleHandling() {
        // EN: Ensure invalid role handling (Optional: if fromString method is implemented)
        // FR: Assurer la gestion des rôles invalides
        assertThatThrownBy(() -> UserRole.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
