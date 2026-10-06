package com.artisthub.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)
    private ArtClass artClass;

    private LocalDate enrollmentDate;

    private String status; // CONFIRMED, PAID, PENDING, CANCELLED

    private Boolean isPaid = false;

    private Double amountPaid = 0.0;

    private String paymentMethod; // "Free Enrollment", "UPI", "Debit Card", "Credit Card", "Net Banking"

    private String transactionId;

    // Constructors
    public Enrollment() {
    }

    public Enrollment(Student student, ArtClass artClass, LocalDate enrollmentDate, String status) {
        this.student = student;
        this.artClass = artClass;
        this.enrollmentDate = enrollmentDate != null ? enrollmentDate : LocalDate.now();
        this.status = status != null ? status : "CONFIRMED";
        this.isPaid = (artClass != null && artClass.getFee() != null && artClass.getFee() > 0);
        this.amountPaid = (artClass != null && artClass.getFee() != null) ? artClass.getFee() : 0.0;
        this.paymentMethod = (artClass != null && artClass.getFee() != null && artClass.getFee() > 0) ? "Online Demo" : "Free Enrollment";
    }

    public Enrollment(Student student, ArtClass artClass, LocalDate enrollmentDate, String status,
                      Boolean isPaid, Double amountPaid, String paymentMethod, String transactionId) {
        this.student = student;
        this.artClass = artClass;
        this.enrollmentDate = enrollmentDate != null ? enrollmentDate : LocalDate.now();
        this.status = status != null ? status : "CONFIRMED";
        this.isPaid = isPaid != null ? isPaid : false;
        this.amountPaid = amountPaid != null ? amountPaid : 0.0;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ArtClass getArtClass() {
        return artClass;
    }

    public void setArtClass(ArtClass artClass) {
        this.artClass = artClass;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
