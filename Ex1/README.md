# [BÀI TẬP 1 - KHÁ] TRIỂN KHAI LOGGING CƠ BẢN VỚI SLF4J & LOMBOK

## 1. Mục tiêu
- **Kiến thức:** Hiểu cách tích hợp thư viện Log SLF4J (Simple Logging Facade for Java) vào Web Service Spring Boot.
- **Kỹ năng:** Sử dụng annotation `@Slf4j` từ thư viện Lombok để ghi nhận các sự kiện diễn ra trong Controller và Service một cách sạch sẽ, không cần khai báo boilerplate code `LoggerFactory.getLogger(...)`.

---

## 2. Mô tả yêu cầu kỹ thuật
- Bổ sung dependency Lombok và sử dụng annotation `@Slf4j` trên `PatientController`.
- Trong hàm `addPatient`:
  - Ghi log mức **`INFO`** hiển thị tên bệnh nhân vừa được gửi lên hệ thống.
  - Ghi log mức **`WARN`** nếu thông tin tuổi bệnh nhân được gửi lên quá cao (`age > 120` tuổi).

---

## 3. Triển khai mã nguồn chính

```java
@Slf4j
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @PostMapping
    public ResponseEntity<PatientDTO> addPatient(@Valid @RequestBody PatientDTO patientDTO) {
        // Ghi log INFO
        log.info("Tiếp nhận bệnh nhân mới: {}", patientDTO.getName());

        // Kiểm tra và ghi log WARN nếu tuổi > 120
        if (patientDTO.getAge() != null && patientDTO.getAge() > 120) {
            log.warn("Cảnh báo: Tuổi bệnh nhân [{}] được gửi lên quá cao ({} tuổi, vượt mức thông thường 120)!",
                    patientDTO.getName(), patientDTO.getAge());
        }

        return new ResponseEntity<>(patientDTO, HttpStatus.CREATED);
    }
}
```

---

## 4. Kết quả trên Console
- **Khi gửi bệnh nhân bình thường (`age = 35`):**
  ```text
  INFO  c.e.m.c.PatientController : Tiếp nhận bệnh nhân mới: Nguyễn Văn A
  ```
- **Khi gửi bệnh nhân có tuổi bất thường (`age = 135`):**
  ```text
  INFO  c.e.m.c.PatientController : Tiếp nhận bệnh nhân mới: Cụ Ông Trần B
  WARN  c.e.m.c.PatientController : Cảnh báo: Tuổi bệnh nhân [Cụ Ông Trần B] được gửi lên quá cao (135 tuổi, vượt mức thông thường 120)!
  ```

---

## 5. Chạy kiểm thử tự động
```bash
cd M:\JAVA_BACKEND\BTVN_moi\SS11\Ex1
mvn test
```
