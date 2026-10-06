package com.artisthub.repository;

import com.artisthub.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByArtClassIdOrderByAskedDateDescIdDesc(Long artClassId);
    long countByArtClassId(Long artClassId);
}
