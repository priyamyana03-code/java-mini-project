package com.artisthub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "course_videos")
public class CourseVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    private Integer lessonNumber; // 1, 2, 3, or 4

    private String duration; // e.g. "15 Mins"

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonIgnoreProperties({"artClasses", "teacher"})
    private ArtClass artClass;

    // Constructors
    public CourseVideo() {
    }

    public CourseVideo(String title, String description, String videoUrl, Integer lessonNumber, String duration, ArtClass artClass) {
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.lessonNumber = lessonNumber;
        this.duration = duration;
        this.artClass = artClass;
    }

    // Helper method: is this video free preview?
    public boolean isFreePreview() {
        if (lessonNumber != null && lessonNumber == 1) {
            return true;
        }
        return artClass != null && (artClass.getFee() == null || artClass.getFee() == 0.0);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(Integer lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArtClass getArtClass() {
        return artClass;
    }

    public void setArtClass(ArtClass artClass) {
        this.artClass = artClass;
    }
}
