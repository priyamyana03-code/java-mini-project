package com.artisthub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String bio;

    private String specialization;

    private String experience;

    private String qualification;

    private String profileImage;

    private Double rating;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("teacher")
    private List<ArtClass> artClasses = new ArrayList<>();

    // Constructors
    public Teacher() {
    }

    public Teacher(String name, String bio, String specialization, String experience, 
                   String qualification, String profileImage, Double rating) {
        this.name = name;
        this.bio = bio;
        this.specialization = specialization;
        this.experience = experience;
        this.qualification = qualification;
        this.profileImage = profileImage;
        this.rating = rating;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<ArtClass> getArtClasses() {
        return artClasses;
    }

    public void setArtClasses(List<ArtClass> artClasses) {
        this.artClasses = artClasses;
    }
}
