package com.example.medical.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {

    private Long id;

    @NotBlank(message = "Tên bệnh nhân không được để trống")
    private String name;

    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 0, message = "Tuổi phải lớn hơn hoặc bằng 0")
    private Integer age;

    private String gender;
    private String phone;
    private String address;
}
