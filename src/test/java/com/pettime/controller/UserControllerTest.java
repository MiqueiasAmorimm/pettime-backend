package com.pettime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pettime.PetTimeApplication;
import com.pettime.dto.UserDto;
import com.pettime.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for UserController with UserDto and UserRole enum.
 * (FR) Tests d'intégration pour UserController avec UserDto et l'énumération UserRole.
 */
@SpringBootTest(classes = PetTimeApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUserWithClientRole() throws Exception {
        UserDto dto = UserDto.builder()
                .name("Alice")
                .email("alice@test.com")
                .password("12345")
                .role(UserRole.CLIENT)
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("CLIENT"))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@test.com"));
    }

    @Test
    void testCreateUserWithAdminRole() throws Exception {

        UserDto dto = UserDto.builder()
                .name("Admin")
                .email("admin@test.com")
                .password("admin123")
                .role(UserRole.ADMIN)
                .build();


        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.name").value("Admin"))
                .andExpect(jsonPath("$.email").value("admin@test.com"));
    }
}
