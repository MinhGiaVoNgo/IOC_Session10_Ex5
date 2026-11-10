package com.example.medical.controller;

import com.example.medical.dto.PatientDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * API tiếp nhận bệnh nhân mới
     * Yêu cầu:
     * - Ghi log INFO hiển thị tên bệnh nhân vừa được gửi lên.
     * - Ghi log WARN nếu thông tin tuổi bệnh nhân > 120.
     */
    @PostMapping
    public ResponseEntity<PatientDTO> addPatient(@Valid @RequestBody PatientDTO patientDTO) {
        log.info("Tiếp nhận bệnh nhân mới: {}", patientDTO.getName());

        if (patientDTO.getAge() != null && patientDTO.getAge() > 120) {
            log.warn("Cảnh báo: Tuổi bệnh nhân [{}] được gửi lên quá cao ({} tuổi, vượt mức thông thường 120)!",
                    patientDTO.getName(), patientDTO.getAge());
        }

        patientDTO.setId(idGenerator.getAndIncrement());
        return new ResponseEntity<>(patientDTO, HttpStatus.CREATED);
    }
}
