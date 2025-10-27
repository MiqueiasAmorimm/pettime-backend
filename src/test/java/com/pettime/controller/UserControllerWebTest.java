package com.pettime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pettime.dto.UserDto;
import com.pettime.model.UserRole;
import com.pettime.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldCreateClientUser() throws Exception {
        UserDto request = UserDto.builder()
                .name("Alice")
                .email("alice@test.com")
                .password("12345")
                .role(UserRole.CLIENT)
                .build();

        when(userService.createUser(any(UserDto.class))).thenReturn(request.toEntity());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("CLIENT"))
                .andExpect(jsonPath("$.name").value("Alice"));
    }
}
