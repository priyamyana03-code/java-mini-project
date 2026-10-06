package com.artisthub.controller;

import com.artisthub.dto.CourseVideoDTO;
import com.artisthub.entity.CourseVideo;
import com.artisthub.service.CourseVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CourseVideoController {

    private final CourseVideoService courseVideoService;

    @Autowired
    public CourseVideoController(CourseVideoService courseVideoService) {
        this.courseVideoService = courseVideoService;
    }

    @GetMapping("/classes/{classId}/videos")
    public ResponseEntity<List<CourseVideoDTO>> getVideosForClass(
            @PathVariable Long classId,
            @RequestParam(required = false) String studentEmail) {
        List<CourseVideoDTO> videos = courseVideoService.getVideosForClass(classId, studentEmail);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable Long id) {
        return courseVideoService.getVideoById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Video not found with ID: " + id)));
    }

    @PutMapping("/videos/{id}")
    public ResponseEntity<?> updateVideo(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        try {
            CourseVideo updated = courseVideoService.updateVideo(
                    id,
                    payload.get("title"),
                    payload.get("description"),
                    payload.get("videoUrl"),
                    payload.get("duration")
            );
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
