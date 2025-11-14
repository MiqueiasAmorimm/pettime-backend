package com.pettime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pettime.dto.UserDto;
import com.pettime.model.UserRole;
import com.pettime.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class UserControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("✅ should create a new client user successfully | (FR) doit créer un nouvel utilisateur client avec succès")
    void shouldCreateClientUser() throws Exception {

        // Request DTO
        UserDto requestUser = new UserDto();
        requestUser.setName("Alice");
        requestUser.setEmail("alice@test.com");
        requestUser.setPassword("12345");
        requestUser.setRole(UserRole.CLIENT);

        // Expected response DTO
        UserDto savedUser = new UserDto();
        savedUser.setId(1L);
        savedUser.setName("Alice");
        savedUser.setEmail("alice@test.com");
        savedUser.setRole(UserRole.CLIENT);

        // Mock correto — retorna UserDto, não User
        when(userService.createUser(ArgumentMatchers.any(UserDto.class)))
                .thenReturn(savedUser);

        // Execute request
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUser)))
                // Assertions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@test.com"))
                .andExpect(jsonPath("$.role").value("CLIENT"));
    }
}
