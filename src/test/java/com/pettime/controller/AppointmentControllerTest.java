package com.pettime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pettime.dto.AppointmentRequestDto;
import com.pettime.model.Appointment;
import com.pettime.model.AppointmentStatus;
import com.pettime.model.Pet;
import com.pettime.model.User;
import com.pettime.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAppointmentSuccessfully() throws Exception {

        AppointmentRequestDto request = new AppointmentRequestDto();
        request.setPetId(1L);
        request.setPetshopId(10L);
        request.setStartTime(LocalDateTime.now().plusHours(1));
        request.setEndTime(LocalDateTime.now().plusHours(2));

        Appointment appointment = Appointment.builder()
                .id(100L)
                .status(AppointmentStatus.PENDING)
                .paid(false)
                .pet(Pet.builder().id(1L).build())
                .petshop(User.builder().id(10L).build())
                .build();

        when(appointmentService.create(
                anyLong(),
                anyLong(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(appointment);


        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.paid").value(false));
    }
}
