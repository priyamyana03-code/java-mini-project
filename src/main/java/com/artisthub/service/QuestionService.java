package com.artisthub.service;

import com.artisthub.entity.ArtClass;
import com.artisthub.entity.Question;
import com.artisthub.repository.ArtClassRepository;
import com.artisthub.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ArtClassRepository artClassRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, ArtClassRepository artClassRepository) {
        this.questionRepository = questionRepository;
        this.artClassRepository = artClassRepository;
    }

    public List<Question> getQuestionsForClass(Long classId) {
        return questionRepository.findByArtClassIdOrderByAskedDateDescIdDesc(classId);
    }

    public Question askQuestion(Long classId, String studentName, String studentEmail, String questionText) {
        if (classId == null) {
            throw new IllegalArgumentException("Class ID is required.");
        }
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (questionText == null || questionText.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty.");
        }

        ArtClass artClass = artClassRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));

        Question question = new Question(
                studentName.trim(),
                studentEmail != null ? studentEmail.trim() : "",
                questionText.trim(),
                null, // answer can be added by teacher later
                LocalDate.now(),
                artClass
        );

        return questionRepository.save(question);
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public long getQuestionCount() {
        return questionRepository.count();
    }
}
