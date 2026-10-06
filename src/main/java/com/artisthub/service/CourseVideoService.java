package com.artisthub.service;

import com.artisthub.dto.CourseVideoDTO;
import com.artisthub.entity.ArtClass;
import com.artisthub.entity.CourseVideo;
import com.artisthub.repository.ArtClassRepository;
import com.artisthub.repository.CourseVideoRepository;
import com.artisthub.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseVideoService {

    private final CourseVideoRepository courseVideoRepository;
    private final ArtClassRepository artClassRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public CourseVideoService(CourseVideoRepository courseVideoRepository,
                              ArtClassRepository artClassRepository,
                              EnrollmentRepository enrollmentRepository) {
        this.courseVideoRepository = courseVideoRepository;
        this.artClassRepository = artClassRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<CourseVideoDTO> getVideosForClass(Long classId, String studentEmail) {
        ArtClass artClass = artClassRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Art class not found with ID: " + classId));

        List<CourseVideo> videos = courseVideoRepository.findByArtClassIdOrderByLessonNumberAsc(classId);

        boolean isFreeCourse = (artClass.getFee() == null || artClass.getFee() == 0.0);
        boolean isEnrolled = false;

        if (studentEmail != null && !studentEmail.trim().isEmpty()) {
            isEnrolled = enrollmentRepository.existsByStudentEmailIgnoreCaseAndArtClassId(studentEmail.trim(), classId);
        }

        List<CourseVideoDTO> dtos = new ArrayList<>();
        for (CourseVideo video : videos) {
            boolean isLocked;

            if (isFreeCourse) {
                // Free courses: all 4 videos unlocked
                isLocked = false;
            } else if (isEnrolled) {
                // Paid courses after purchase: all 4 videos unlocked
                isLocked = false;
            } else {
                // Paid courses before purchase: only Lesson 1 unlocked, 2/3/4 locked
                isLocked = (video.getLessonNumber() != null && video.getLessonNumber() > 1);
            }

            // For security: if video is locked, hide the actual video URL
            String videoUrl = isLocked ? null : video.getVideoUrl();

            dtos.add(new CourseVideoDTO(
                    video.getId(),
                    video.getTitle(),
                    video.getDescription(),
                    video.getLessonNumber(),
                    video.getDuration(),
                    artClass.getId(),
                    artClass.getName(),
                    isLocked,
                    videoUrl,
                    video.isFreePreview()
            ));
        }

        return dtos;
    }

    public CourseVideo saveVideo(CourseVideo video) {
        return courseVideoRepository.save(video);
    }

    public Optional<CourseVideo> getVideoById(Long id) {
        return courseVideoRepository.findById(id);
    }

    public CourseVideo updateVideo(Long id, String title, String description, String videoUrl, String duration) {
        CourseVideo video = courseVideoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found with ID: " + id));

        if (title != null && !title.trim().isEmpty()) video.setTitle(title.trim());
        if (description != null) video.setDescription(description.trim());
        if (videoUrl != null && !videoUrl.trim().isEmpty()) video.setVideoUrl(videoUrl.trim());
        if (duration != null) video.setDuration(duration.trim());

        return courseVideoRepository.save(video);
    }

    public long getVideoCount() {
        return courseVideoRepository.count();
    }
}
