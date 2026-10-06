package com.artisthub.controller;

import com.artisthub.entity.Enrollment;
import com.artisthub.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/student/{email}")
    public ResponseEntity<List<Enrollment>> getStudentEnrollments(@PathVariable String email) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentEmail(email));
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkEnrollment(
            @RequestParam String email,
            @RequestParam Long classId) {
        boolean enrolled = enrollmentService.isStudentEnrolled(email, classId);
        return ResponseEntity.ok(Map.of("enrolled", enrolled));
    }

    @PostMapping
    public ResponseEntity<?> enrollStudent(@RequestBody EnrollmentRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (request.getStudentName() == null || request.getStudentName().trim().isEmpty()) {
            response.put("error", "Student name cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        if (request.getStudentEmail() == null || !request.getStudentEmail().contains("@")) {
            response.put("error", "A valid email address is required.");
            return ResponseEntity.badRequest().body(response);
        }
        if (request.getStudentPhone() == null || request.getStudentPhone().trim().isEmpty()) {
            response.put("error", "Phone number cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        if (request.getClassId() == null) {
            response.put("error", "Art class ID must be provided.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Enrollment enrollment = enrollmentService.enrollOrPurchase(
                    request.getStudentName(),
                    request.getStudentEmail(),
                    request.getStudentPhone(),
                    request.getClassId(),
                    request.getPaymentMethod() != null ? request.getPaymentMethod() : "Free Enrollment",
                    request.getAmountPaid() != null ? request.getAmountPaid() : 0.0
            );

            response.put("message", "Enrollment successful! Welcome to the class.");
            response.put("enrollment", enrollment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "Failed to process enrollment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseCourse(@RequestBody PurchaseRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (request.getStudentName() == null || request.getStudentName().trim().isEmpty()) {
            response.put("error", "Student name cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        if (request.getStudentEmail() == null || !request.getStudentEmail().contains("@")) {
            response.put("error", "A valid email address is required.");
            return ResponseEntity.badRequest().body(response);
        }
        if (request.getClassId() == null) {
            response.put("error", "Course ID must be provided.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String phone = request.getStudentPhone() != null && !request.getStudentPhone().trim().isEmpty() 
                    ? request.getStudentPhone() : "+91 9876543210";
            String method = request.getPaymentMethod() != null && !request.getPaymentMethod().trim().isEmpty() 
                    ? request.getPaymentMethod() : "UPI";

            Enrollment enrollment = enrollmentService.enrollOrPurchase(
                    request.getStudentName(),
                    request.getStudentEmail(),
                    phone,
                    request.getClassId(),
                    method,
                    request.getAmountPaid()
            );

            response.put("message", "Congratulations! You have successfully enrolled in this course.");
            response.put("transactionId", enrollment.getTransactionId());
            response.put("amountPaid", enrollment.getAmountPaid());
            response.put("courseName", enrollment.getArtClass().getName());
            response.put("enrollment", enrollment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("error", "Demo payment failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // DTO for standard enrollment
    public static class EnrollmentRequest {
        private String studentName;
        private String studentEmail;
        private String studentPhone;
        private Long classId;
        private String paymentMethod;
        private Double amountPaid;

        public EnrollmentRequest() {}

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }
        public String getStudentEmail() { return studentEmail; }
        public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
        public String getStudentPhone() { return studentPhone; }
        public void setStudentPhone(String studentPhone) { this.studentPhone = studentPhone; }
        public Long getClassId() { return classId; }
        public void setClassId(Long classId) { this.classId = classId; }
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        public Double getAmountPaid() { return amountPaid; }
        public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
    }

    // DTO for demo course purchase
    public static class PurchaseRequest {
        private String studentName;
        private String studentEmail;
        private String studentPhone;
        private Long classId;
        private String paymentMethod;
        private Double amountPaid;

        public PurchaseRequest() {}

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }
        public String getStudentEmail() { return studentEmail; }
        public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
        public String getStudentPhone() { return studentPhone; }
        public void setStudentPhone(String studentPhone) { this.studentPhone = studentPhone; }
        public Long getClassId() { return classId; }
        public void setClassId(Long classId) { this.classId = classId; }
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        public Double getAmountPaid() { return amountPaid; }
        public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
    }
}
