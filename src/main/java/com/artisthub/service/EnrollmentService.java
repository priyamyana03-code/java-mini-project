package com.artisthub.service;

import com.artisthub.entity.ArtClass;
import com.artisthub.entity.Enrollment;
import com.artisthub.entity.Student;
import com.artisthub.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final ArtClassService artClassService;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentService studentService,
                             ArtClassService artClassService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.artClassService = artClassService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getEnrollmentsByStudentEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return List.of();
        }
        return enrollmentRepository.findByStudentEmailIgnoreCase(email.trim());
    }

    public boolean isStudentEnrolled(String email, Long classId) {
        if (email == null || email.trim().isEmpty() || classId == null) {
            return false;
        }
        return enrollmentRepository.existsByStudentEmailIgnoreCaseAndArtClassId(email.trim(), classId);
    }

    @Transactional
    public Enrollment enrollStudent(String studentName, String studentEmail, String studentPhone, Long classId) {
        return enrollOrPurchase(studentName, studentEmail, studentPhone, classId, "Free Enrollment", 0.0);
    }

    @Transactional
    public Enrollment enrollOrPurchase(String studentName, String studentEmail, String studentPhone,
                                       Long classId, String paymentMethod, Double amountPaid) {
        if (classId == null) {
            throw new IllegalArgumentException("Class ID is required for enrollment.");
        }
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (studentEmail == null || !studentEmail.contains("@")) {
            throw new IllegalArgumentException("A valid email address is required.");
        }
        if (studentPhone == null || studentPhone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }

        ArtClass artClass = artClassService.getClassById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));

        String email = studentEmail.trim().toLowerCase();

        // Check if student already enrolled
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentEmailIgnoreCaseAndArtClassId(email, classId);
        if (existingEnrollment.isPresent()) {
            Enrollment current = existingEnrollment.get();
            // If already enrolled, return existing
            return current;
        }

        int max = artClass.getMaxStudents() != null ? artClass.getMaxStudents() : 0;
        int enrolled = artClass.getEnrolledStudents() != null ? artClass.getEnrolledStudents() : 0;

        if (enrolled >= max && max > 0) {
            throw new IllegalStateException("Sorry, this class is full! Maximum capacity of " + max + " students reached.");
        }

        // Save or update student
        Student student = studentService.saveOrUpdateStudent(new Student(studentName, email, studentPhone));

        // Increment enrolled students
        artClass.setEnrolledStudents(enrolled + 1);
        artClassService.saveClass(artClass);

        boolean isPaid = (artClass.getFee() != null && artClass.getFee() > 0);
        Double finalAmount = (amountPaid != null && amountPaid > 0) ? amountPaid : (artClass.getFee() != null ? artClass.getFee() : 0.0);
        String finalMethod = paymentMethod != null && !paymentMethod.trim().isEmpty() ? paymentMethod : (isPaid ? "Demo Card" : "Free Enrollment");
        String txnId = "TXN-ART-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Enrollment enrollment = new Enrollment(
                student,
                artClass,
                LocalDate.now(),
                "CONFIRMED",
                isPaid,
                finalAmount,
                finalMethod,
                txnId
        );

        return enrollmentRepository.save(enrollment);
    }
}
