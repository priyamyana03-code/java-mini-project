package com.artisthub.repository;

import com.artisthub.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findBySpecializationContainingIgnoreCase(String specialization);
}
