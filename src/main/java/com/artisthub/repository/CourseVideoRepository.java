package com.artisthub.repository;

import com.artisthub.entity.CourseVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseVideoRepository extends JpaRepository<CourseVideo, Long> {
    List<CourseVideo> findByArtClassIdOrderByLessonNumberAsc(Long artClassId);
    long countByArtClassId(Long artClassId);
}
