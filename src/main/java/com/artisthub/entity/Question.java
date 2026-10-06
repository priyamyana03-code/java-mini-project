package com.artisthub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentName;

    private String studentEmail;

    @Column(length = 1000, nullable = false)
    private String questionText;

    @Column(length = 1000)
    private String answerText;

    private LocalDate askedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonIgnoreProperties({"artClasses", "teacher"})
    private ArtClass artClass;

    // Constructors
    public Question() {
    }

    public Question(String studentName, String studentEmail, String questionText, String answerText, LocalDate askedDate, ArtClass artClass) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.questionText = questionText;
        this.answerText = answerText;
        this.askedDate = askedDate != null ? askedDate : LocalDate.now();
        this.artClass = artClass;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public LocalDate getAskedDate() {
        return askedDate;
    }

    public void setAskedDate(LocalDate askedDate) {
        this.askedDate = askedDate;
    }

    public ArtClass getArtClass() {
        return artClass;
    }

    public void setArtClass(ArtClass artClass) {
        this.artClass = artClass;
    }
}
