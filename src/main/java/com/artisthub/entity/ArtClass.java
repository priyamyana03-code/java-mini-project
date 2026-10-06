package com.artisthub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "art_classes")
public class ArtClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category; // Painting, Sketching, Pastel, Doodling

    @Column(length = 1000)
    private String description;

    private String level; // Beginner, Intermediate, Advanced

    private String mode; // Online, Offline

    private String duration; // e.g. "4 Weeks", "2 Hours"

    private Integer sessions;

    private Double fee; // ₹0 or amount

    private String date; // e.g. "2026-10-15"

    private String startTime; // e.g. "10:00 AM"

    private String endTime; // e.g. "11:30 AM"

    private Integer maxStudents;

    private Integer enrolledStudents = 0;

    private String address;

    private String city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    @JsonIgnoreProperties("artClasses")
    private Teacher teacher;

    // Constructors
    public ArtClass() {
    }

    public ArtClass(String name, String category, String description, String level, 
                    String mode, String duration, Integer sessions, Double fee, 
                    String date, String startTime, String endTime, Integer maxStudents, 
                    Integer enrolledStudents, String address, String city, Teacher teacher) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.level = level;
        this.mode = mode;
        this.duration = duration;
        this.sessions = sessions;
        this.fee = fee;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxStudents = maxStudents;
        this.enrolledStudents = enrolledStudents != null ? enrolledStudents : 0;
        this.address = address;
        this.city = city;
        this.teacher = teacher;
    }

    // Helper getter for available seats
    public Integer getAvailableSeats() {
        int max = maxStudents != null ? maxStudents : 0;
        int enrolled = enrolledStudents != null ? enrolledStudents : 0;
        return Math.max(0, max - enrolled);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getSessions() {
        return sessions;
    }

    public void setSessions(Integer sessions) {
        this.sessions = sessions;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public Integer getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Integer enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
