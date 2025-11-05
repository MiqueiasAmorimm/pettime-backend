package com.pettime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pettime.model.User;
import com.pettime.model.UserRole;
import com.pettime.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 🧪 Integration tests for UserController.
 * Tests d'intégration pour UserController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class UserControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    UserControllerWebTest(UserService userService) {
    }

    @Test
    void shouldCreateClientUser() throws Exception {
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@test.com");
        user.setPassword("12345");
        user.setRole(UserRole.valueOf("CLIENT"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }
}
