package com.artisthub.repository;

import com.artisthub.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByArtClassId(Long artClassId);
    boolean existsByStudentIdAndArtClassId(Long studentId, Long artClassId);

    List<Enrollment> findByStudentEmailIgnoreCase(String email);
    boolean existsByStudentEmailIgnoreCaseAndArtClassId(String email, Long artClassId);
    Optional<Enrollment> findByStudentEmailIgnoreCaseAndArtClassId(String email, Long artClassId);
}
