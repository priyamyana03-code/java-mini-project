package com.artisthub.controller;

import com.artisthub.entity.Student;
import com.artisthub.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final StudentService studentService;

    @Autowired
    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Student student = studentService.register(
                    request.getName(),
                    request.getEmail(),
                    request.getPhone(),
                    request.getPassword()
            );
            response.put("message", "Registration successful! Welcome to Artist Hub.");
            response.put("student", sanitizeStudent(student));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("error", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Student student = studentService.authenticate(request.getEmail(), request.getPassword());
            response.put("message", "Login successful!");
            response.put("student", sanitizeStudent(student));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.put("error", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestParam String email) {
        return studentService.getStudentByEmail(email)
                .<ResponseEntity<?>>map(s -> ResponseEntity.ok(sanitizeStudent(s)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Student profile not found.")));
    }

    private Map<String, Object> sanitizeStudent(Student student) {
        Map<String, Object> safe = new HashMap<>();
        safe.put("id", student.getId());
        safe.put("name", student.getName());
        safe.put("email", student.getEmail());
        safe.put("phone", student.getPhone());
        return safe;
    }

    // DTOs
    public static class RegisterRequest {
        private String name;
        private String email;
        private String phone;
        private String password;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
