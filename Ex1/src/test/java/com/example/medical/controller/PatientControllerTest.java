package com.example.medical.controller;

import com.example.medical.dto.PatientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/v1/patients - Tiếp nhận bệnh nhân bình thường (ghi log INFO)")
    void testAddPatient_NormalAge() throws Exception {
        PatientDTO patient = PatientDTO.builder()
                .name("Nguyễn Văn A")
                .age(35)
                .gender("Nam")
                .phone("0987654321")
                .address("Hà Nội")
                .build();

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Nguyễn Văn A"))
                .andExpect(jsonPath("$.age").value(35));
    }

    @Test
    @DisplayName("POST /api/v1/patients - Bệnh nhân tuổi > 120 (ghi log WARN cảnh báo)")
    void testAddPatient_ElderlyAgeWarn() throws Exception {
        PatientDTO patient = PatientDTO.builder()
                .name("Cụ Ông Trần B")
                .age(135) // Tuổi > 120
                .gender("Nam")
                .phone("0912345678")
                .address("TP. Hồ Chí Minh")
                .build();

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Cụ Ông Trần B"))
                .andExpect(jsonPath("$.age").value(135));
    }
}
