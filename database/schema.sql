-- ===================================================================
-- ARTIST HUB - MySQL Database Schema (Updated with Course Videos & Questions)
-- ===================================================================

CREATE DATABASE IF NOT EXISTS artist_hub;
USE artist_hub;

-- 1. Teachers Table
CREATE TABLE IF NOT EXISTS teachers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio VARCHAR(1000),
    specialization VARCHAR(255),
    experience VARCHAR(255),
    qualification VARCHAR(255),
    profile_image VARCHAR(255),
    rating DOUBLE
);

-- 2. Art Classes Table
CREATE TABLE IF NOT EXISTS art_classes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    description VARCHAR(1000),
    level VARCHAR(255),
    mode VARCHAR(255),
    duration VARCHAR(255),
    sessions INT,
    fee DOUBLE,
    date VARCHAR(255),
    start_time VARCHAR(255),
    end_time VARCHAR(255),
    max_students INT,
    enrolled_students INT DEFAULT 0,
    address VARCHAR(255),
    city VARCHAR(255),
    teacher_id BIGINT,
    CONSTRAINT fk_class_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE SET NULL
);

-- 3. Students Table (with password for authentication)
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL,
    password VARCHAR(255) DEFAULT 'password123'
);

-- 4. Enrollments Table (with demo payment fields)
CREATE TABLE IF NOT EXISTS enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    class_id BIGINT NOT NULL,
    enrollment_date DATE,
    status VARCHAR(255) DEFAULT 'CONFIRMED',
    is_paid BOOLEAN DEFAULT FALSE,
    amount_paid DOUBLE DEFAULT 0.0,
    payment_method VARCHAR(255),
    transaction_id VARCHAR(255),
    CONSTRAINT fk_enroll_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_enroll_class FOREIGN KEY (class_id) REFERENCES art_classes(id) ON DELETE CASCADE
);

-- 5. Course Videos Table (4 Lessons per course)
CREATE TABLE IF NOT EXISTS course_videos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    video_url VARCHAR(255) NOT NULL,
    lesson_number INT NOT NULL,
    duration VARCHAR(255),
    class_id BIGINT NOT NULL,
    CONSTRAINT fk_video_class FOREIGN KEY (class_id) REFERENCES art_classes(id) ON DELETE CASCADE
);

-- 6. Questions Table (Ask a Question feature)
CREATE TABLE IF NOT EXISTS questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    student_email VARCHAR(255),
    question_text VARCHAR(1000) NOT NULL,
    answer_text VARCHAR(1000),
    asked_date DATE,
    class_id BIGINT NOT NULL,
    CONSTRAINT fk_question_class FOREIGN KEY (class_id) REFERENCES art_classes(id) ON DELETE CASCADE
);

-- 7. Reviews Table
CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    class_name VARCHAR(255) NOT NULL,
    rating INT NOT NULL,
    comment VARCHAR(1000) NOT NULL,
    review_date DATE
);
