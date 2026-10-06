package com.artisthub.service;

import com.artisthub.entity.Student;
import com.artisthub.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email != null ? email.trim().toLowerCase() : "");
    }

    public Student register(String name, String email, String phone, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("A valid email address is required.");
        }
        if (password == null || password.trim().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required.");
        }

        String normalizedEmail = email.trim().toLowerCase();
        if (studentRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists. Please login instead.");
        }

        Student student = new Student(name.trim(), normalizedEmail, phone.trim(), password.trim());
        return studentRepository.save(student);
    }

    public Student authenticate(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }

        String normalizedEmail = email.trim().toLowerCase();
        Student student = studentRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new IllegalArgumentException("No account found with this email. Please register first."));

        if (student.getPassword() != null && !student.getPassword().equals(password.trim())) {
            throw new IllegalArgumentException("Incorrect password. Please try again.");
        }

        return student;
    }

    public Student saveOrUpdateStudent(Student student) {
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (student.getEmail() == null || !student.getEmail().contains("@")) {
            throw new IllegalArgumentException("A valid email address is required.");
        }
        if (student.getPhone() == null || student.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }

        String email = student.getEmail().trim().toLowerCase();
        student.setEmail(email);
        student.setName(student.getName().trim());
        student.setPhone(student.getPhone().trim());

        // Check if student with this email already exists, update info or return existing
        Optional<Student> existing = studentRepository.findByEmail(email);
        if (existing.isPresent()) {
            Student s = existing.get();
            s.setName(student.getName());
            s.setPhone(student.getPhone());
            if (student.getPassword() != null && !student.getPassword().isEmpty()) {
                s.setPassword(student.getPassword());
            }
            return studentRepository.save(s);
        }

        if (student.getPassword() == null || student.getPassword().isEmpty()) {
            student.setPassword("password123");
        }

        return studentRepository.save(student);
    }
}
