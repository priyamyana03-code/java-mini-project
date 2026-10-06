package com.artisthub.controller;

import com.artisthub.entity.Question;
import com.artisthub.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Question>> getQuestionsForClass(@PathVariable Long classId) {
        return ResponseEntity.ok(questionService.getQuestionsForClass(classId));
    }

    @PostMapping
    public ResponseEntity<?> askQuestion(@RequestBody QuestionRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (request.getClassId() == null) {
            response.put("error", "Class ID is required.");
            return ResponseEntity.badRequest().body(response);
        }
        if (request.getStudentName() == null || request.getStudentName().trim().isEmpty()) {
            response.put("error", "Student name is required.");
            return ResponseEntity.badRequest().body(response);
        }
        if (request.getQuestionText() == null || request.getQuestionText().trim().isEmpty()) {
            response.put("error", "Question text cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Question question = questionService.askQuestion(
                    request.getClassId(),
                    request.getStudentName(),
                    request.getStudentEmail(),
                    request.getQuestionText()
            );

            response.put("message", "Question submitted successfully! Your instructor will respond soon.");
            response.put("question", question);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("error", "Failed to submit question: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public static class QuestionRequest {
        private Long classId;
        private String studentName;
        private String studentEmail;
        private String questionText;

        public QuestionRequest() {}

        public Long getClassId() { return classId; }
        public void setClassId(Long classId) { this.classId = classId; }
        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }
        public String getStudentEmail() { return studentEmail; }
        public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }
    }
}
