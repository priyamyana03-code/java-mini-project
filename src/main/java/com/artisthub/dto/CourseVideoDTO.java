package com.artisthub.dto;

public class CourseVideoDTO {
    private Long id;
    private String title;
    private String description;
    private Integer lessonNumber;
    private String duration;
    private Long classId;
    private String className;
    private Boolean isLocked;
    private String videoUrl;
    private Boolean isFreePreview;

    public CourseVideoDTO() {
    }

    public CourseVideoDTO(Long id, String title, String description, Integer lessonNumber,
                          String duration, Long classId, String className, Boolean isLocked,
                          String videoUrl, Boolean isFreePreview) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lessonNumber = lessonNumber;
        this.duration = duration;
        this.classId = classId;
        this.className = className;
        this.isLocked = isLocked;
        this.videoUrl = videoUrl;
        this.isFreePreview = isFreePreview;
    }

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

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Boolean getIsFreePreview() {
        return isFreePreview;
    }

    public void setIsFreePreview(Boolean isFreePreview) {
        this.isFreePreview = isFreePreview;
    }
}
