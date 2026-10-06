# Database Setup - ARTIST HUB

This document provides clear, step-by-step instructions for configuring the **MySQL** database for the **Artist Hub** project.

---

## 1. Quick Setup (Recommended)

Spring Boot and Hibernate are configured with:
```properties
spring.jpa.hibernate.ddl-auto=update
```
This means Hibernate will **automatically create all necessary tables, columns, foreign keys, and sequences** as soon as the Spring Boot application starts!

You only need to create the empty database in MySQL.

### Step 1: Open MySQL Command Line Client or Terminal
```bash
mysql -u root -p
```
*(Enter your MySQL root password when prompted)*

### Step 2: Create the Database
```sql
CREATE DATABASE artist_hub;
```

### Step 3: Verify Creation
```sql
SHOW DATABASES;
USE artist_hub;
```

---

## 2. Configure `application.properties`

Open [application.properties](file:///src/main/resources/application.properties) and update your MySQL password:

```properties
# MySQL Database Connection
spring.datasource.url=jdbc:mysql://localhost:3306/artist_hub?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate Auto-DDL
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **IMPORTANT:** Replace `YOUR_PASSWORD` with the actual password you set during MySQL installation (e.g. `root`, `admin123`, etc.). Do not push your personal password to GitHub.

---

## 3. Entity & Table Structure

Once the application runs, Hibernate will create the following 5 relational tables:

### 1. `teachers`
| Column | Type | Description |
|---|---|---|
| `id` | BIGINT AUTO_INCREMENT PRIMARY KEY | Unique teacher ID |
| `name` | VARCHAR(255) NOT NULL | Full name of teacher |
| `bio` | VARCHAR(1000) | Teacher's artistic background |
| `specialization` | VARCHAR(255) | Mediums (e.g., Watercolor, Charcoal) |
| `experience` | VARCHAR(255) | Years of experience (e.g., "8+ Years") |
| `qualification` | VARCHAR(255) | Fine arts degree (e.g., MFA, BFA) |
| `profile_image` | VARCHAR(255) | Image URL |
| `rating` | DOUBLE | Mentor rating (1.0 to 5.0) |

### 2. `art_classes`
| Column | Type | Description |
|---|---|---|
| `id` | BIGINT AUTO_INCREMENT PRIMARY KEY | Unique class ID |
| `name` | VARCHAR(255) NOT NULL | Course title |
| `category` | VARCHAR(255) | Painting, Sketching, Pastel, Doodling |
| `description` | VARCHAR(1000) | Class overview & curriculum |
| `level` | VARCHAR(255) | Beginner, Intermediate, Advanced |
| `mode` | VARCHAR(255) | Online or Offline |
| `duration` | VARCHAR(255) | e.g. "4 Weeks", "2 Hours" |
| `sessions` | INT | Number of sessions |
| `fee` | DOUBLE | Fee in ₹ (₹0 for Free Doodling Class) |
| `date` | VARCHAR(255) | Scheduled start date |
| `start_time` | VARCHAR(255) | Start time (e.g. "10:00 AM") |
| `end_time` | VARCHAR(255) | End time (e.g. "11:30 AM") |
| `max_students` | INT | Maximum seating capacity |
| `enrolled_students`| INT | Currently enrolled count |
| `address` | VARCHAR(255) | Physical studio address or Zoom link |
| `city` | VARCHAR(255) | City (Mumbai, Pune, Bengaluru, etc.) |
| `teacher_id` | BIGINT FOREIGN KEY | Foreign key to `teachers(id)` |

### 3. `students`
| Column | Type | Description |
|---|---|---|
| `id` | BIGINT AUTO_INCREMENT PRIMARY KEY | Unique student ID |
| `name` | VARCHAR(255) NOT NULL | Student full name |
| `email` | VARCHAR(255) NOT NULL | Student contact email |
| `phone` | VARCHAR(255) NOT NULL | Student phone number |

### 4. `enrollments`
| Column | Type | Description |
|---|---|---|
| `id` | BIGINT AUTO_INCREMENT PRIMARY KEY | Unique enrollment ID |
| `student_id` | BIGINT FOREIGN KEY | Foreign key to `students(id)` |
| `class_id` | BIGINT FOREIGN KEY | Foreign key to `art_classes(id)` |
| `enrollment_date` | DATE | Date of enrollment |
| `status` | VARCHAR(255) | Enrollment status ("CONFIRMED") |

### 5. `reviews`
| Column | Type | Description |
|---|---|---|
| `id` | BIGINT AUTO_INCREMENT PRIMARY KEY | Unique review ID |
| `student_name` | VARCHAR(255) NOT NULL | Name of reviewer |
| `class_name` | VARCHAR(255) NOT NULL | Title of attended class |
| `rating` | INT NOT NULL | Score between 1 and 5 |
| `comment` | VARCHAR(1000) NOT NULL | Student feedback text |
| `review_date` | DATE | Date review was submitted |

---

## 4. Manual SQL Schema & Sample Data (Optional)

If your college examiner asks to view raw SQL script files instead of relying on Hibernate auto-generation, you can inspect:
- `database/schema.sql` (Table definitions)
- `database/sample_data.sql` (Seed queries)
