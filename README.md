# ARTIST HUB
### A Java-Based Artist Learning & Teaching Platform
> **College Java Mini Project** | Java 17/21 • Spring Boot 3 • Spring Data JPA • MySQL • Bootstrap 5

---

## 1. Project Title & Tagline
**ARTIST HUB: A Java-Based Artist Learning & Teaching Platform**  
*Tagline: "Discover • Learn • Create"*

---

## 2. Project Description
**Artist Hub** is an interactive, web-based educational platform connecting fine arts mentors with students seeking structured art training. The platform allows students to:
- Discover verified art teachers and browse their portfolios.
- Search and filter art classes across 4 key disciplines: **Painting**, **Sketching**, **Pastel**, and **Doodling**.
- Access structured **4-lesson video curriculum** per course with an embedded HTML5 video player.
- Experience seamless **Access Control**:
  - **Free Courses** (e.g. Beginner Doodling Class): All 4 video lessons are 100% unlocked (`✓ Lesson 1-4`).
  - **Paid Courses**: Lesson 1 is unlocked as a free preview (`▶ Lesson 1`); Lessons 2, 3, and 4 are visually and programmatically locked (`🔒 Locked`).
- Complete a realistic **Demo Course Purchase Flow** (UPI, Debit Card, Credit Card, Net Banking) without real gateways or sensitive credentials.
- View immediate **Payment Success** screens with generated Transaction IDs and instant video unlocking.
- Register and sign in with simple student accounts (`Student` entity).
- Manage learning in a dedicated **Student Profile & "My Courses"** section.
- Ask questions and view mentor answers in a dedicated **Q&A Section** under each course.
- Consult tailored **Recommended Art Supplies** for every art medium.

---

## 3. Technology Stack

- **Backend**:
  - **Java 17 / 21**
  - **Spring Boot 3.2.4**
  - **Spring Web** (RESTful API controllers)
  - **Spring Data JPA & Hibernate 6** (ORM & query methods)
  - **Maven** (Dependency management & packaging)
- **Database**:
  - **MySQL 8.x** (Primary relational database)
  - **H2 In-Memory Database** (Zero-dependency fallback profile for instant viva demonstration)
- **Frontend**:
  - **HTML5 & Vanilla CSS**
  - **Bootstrap 5.3.3** (Responsive grid, cards, modals)
  - **Vanilla JavaScript (ES6)** (Native `fetch()`, DOM manipulation, local storage)
  - **Font Awesome 6.5.2** (Icons, lock indicators, star ratings)

---

## 4. Key Features & Business Rules

### 1. Course Videos & Video Learning
- Every course contains **exactly 4 video lessons** (e.g., Intro, Fundamental Techniques, Intermediate Exercises, Final Project).
- Played using standard HTML5 `<video controls>` element with smooth playlist switching.
- Stored in the `course_videos` table via the `CourseVideo` entity.
- Easily replaceable via REST API (`PUT /api/videos/{id}`) or direct database updates.

### 2. Free Course Access
- For free classes (fee = 0, such as **Beginner Doodling Class**):
  - All 4 video lessons are accessible without any locks (`✓ Lesson 1`, `✓ Lesson 2`, `✓ Lesson 3`, `✓ Lesson 4`).

### 3. Paid Course Access & Video Locking
- Before purchase:
  - Video 1 is accessible (`▶ Lesson 1 - Free Preview`).
  - Videos 2, 3, and 4 are locked (`🔒 Lesson 2 - Locked`, etc.).
  - Clicking a locked lesson displays a clear locked overlay:  
    *"This lesson is locked. Purchase this course to unlock all 4 lessons."* along with a **"Buy Course to Unlock"** button.
  - The backend masks `videoUrl` (returns `null` and `isLocked: true`) unless the student is enrolled.

### 4. Demo Purchase Flow
- Clicking "Buy Course" opens a student-friendly demo checkout modal displaying:
  - Course Name & Fee
  - Student Name
  - Payment Options: **UPI**, **Debit Card**, **Credit Card**, **Net Banking**
  - Contextual mock input fields (e.g., UPI ID, Card Number, Expiry, CVV, Bank Selection).
  - *No real money is charged; no sensitive credentials are stored.*

### 5. Payment Success & Course Unlocking
- After submitting demo payment:
  - Shows a professional success modal:  
    `✓ Payment Successful!`  
    Course Name, Amount Paid, and generated Transaction ID (`TXN-ART-XXXXXXXX`).
  - Marks the course as enrolled/purchased in MySQL (`is_paid = true`).
  - Immediately unlocks Lessons 1, 2, 3, and 4.
  - Enables "Start Learning Now" button that scrolls directly into the video player.

### 6. User Account & Authentication
- Clean Login and Registration tabs at `login.html`.
- Student fields: `name`, `email`, `phone`, `password`.
- Pre-seeded demo account:
  - **Email**: `priyam@example.com`
  - **Password**: `password123`
- Dynamic navigation bar automatically updates:
  - Logged Out: `Login / Register`
  - Logged In: `Profile`, `My Courses`, and `Logout`

### 7. Profile & "My Courses"
- Located at `profile.html`.
- Displays student avatar, name, email, phone, enrolled course count, and purchased course count.
- Under "My Courses", lists each enrolled class with mentor details, transaction ID, payment badge (`Free Enrolled` vs `Purchased`), and a **"Continue Learning"** button.

### 8. Course Q&A ("Ask a Question")
- Built into each course details page.
- Students can submit technical questions to the mentor.
- Displays past student questions with instructor responses.

### 9. Recommended Art Supplies
- Under every course, displays tailored supply cards based on discipline:
  - **Painting**: Watercolor/Acrylic paints, Brushes, Heavy canvas/300 GSM paper, Palette & Cup.
  - **Sketching**: Graphite pencils (HB-6B), Heavy sketchbook, Kneaded eraser, Blending stumps.
  - **Doodling**: Waterproof fineliners, Smooth unruled notebook, Gel pens/markers, Stencil ruler.
  - **Pastel**: Soft pastel sticks (24+ shades), Textured pastel paper, Silicone tools, Fixative spray.

### 10. Clean About Page
- Removed technical architecture and stack diagrams.
- Replaced with the "Learn. Create. Share." community philosophy.

---

## 5. Database Entities & Tables

```
                    ┌─────────────────────────┐
                    │        teachers         │
                    │─────────────────────────│
                    │ id (PK)                 │
                    │ name                    │
                    │ specialization          │
                    │ experience              │
                    │ qualification           │
                    │ rating                  │
                    │ profile_image           │
                    │ bio                     │
                    └───────────┬─────────────┘
                                │ 1
                                │
                                │ *
┌─────────────────────────┐     ▼     ┌─────────────────────────┐
│        students         │◄────┼─────│       art_classes       │
│─────────────────────────│     │     │─────────────────────────│
│ id (PK)                 │     │     │ id (PK)                 │
│ name                    │     │     │ name                    │
│ email (UNIQUE)          │     │     │ category                │
│ phone                   │     │     │ description             │
│ password                │     │     │ level, mode, fee        │
└───────────┬─────────────┘     │     │ teacher_id (FK)         │
            │                   │     └─────┬─────────────┬─────┘
            │                   │           │ 1           │ 1
            │ 1                 │           │             │
            │                   │           │ *           │ *
            ▼ *                 │           ▼             ▼
┌─────────────────────────┐     │     ┌───────────────┐ ┌───────────────┐
│       enrollments       │     │     │ course_videos │ │   questions   │
│─────────────────────────│     │     │───────────────│ │───────────────│
│ id (PK)                 │     │     │ id (PK)       │ │ id (PK)       │
│ student_id (FK)         │─────┘     │ class_id (FK) │ │ class_id (FK) │
│ class_id (FK)           │           │ title         │ │ student_name  │
│ is_paid                 │           │ description   │ │ student_email │
│ amount_paid             │           │ video_url     │ │ question_text │
│ payment_method          │           │ lesson_number │ │ answer_text   │
│ transaction_id          │           │ is_free_prev  │ │ asked_date    │
│ enrollment_date         │           │ duration      │ └───────────────┘
│ status                  │           └───────────────┘
└─────────────────────────┘
```

---

## 6. How to Add or Replace Video URLs

Each course has 4 video lessons stored in the database. To point them to your own uploaded files:

### Method A: Via REST API (No Database tool needed)
Send an HTTP `PUT` request to `/api/videos/{id}`:
```bash
curl -X PUT http://localhost:8080/api/videos/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "Lesson 1 - My New Lesson", "videoUrl": "videos/my-video.mp4"}'
```

### Method B: Via MySQL Database
```sql
UPDATE course_videos 
SET video_url = 'videos/my-custom-video.mp4', title = 'My Custom Title' 
WHERE id = 1;
```

### Local Video Files Location
Place `.mp4` video files in:
```
src/main/resources/static/videos/
```
Then set `videoUrl` to `videos/filename.mp4`.

---

## 7. How to Run the Project

### Option A: Standard Mode (with MySQL)
1. Start your local MySQL service.
2. In MySQL, ensure the database exists:
   ```sql
   CREATE DATABASE IF NOT EXISTS artist_hub;
   ```
3. Update MySQL password in `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```
4. Run using the Maven wrapper:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

### Option B: Zero-Dependency Mode (In-Memory H2 for Viva Demo)
If MySQL is not installed or running during presentation:
```powershell
java -jar target/artist-hub-1.0.0.jar --spring.profiles.active=h2
```
or
```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=h2
```

---

## 8. Browser Access & Pages

Open your web browser and visit:
```
http://localhost:8080
```

- **Home Page**: `http://localhost:8080/index.html`
- **Class Catalog**: `http://localhost:8080/classes.html`
- **Course Detail & Video Lessons**: `http://localhost:8080/course-detail.html?id=1`
- **Student Login & Registration**: `http://localhost:8080/login.html`
- **Student Profile & My Courses**: `http://localhost:8080/profile.html`
- **Teachers Directory**: `http://localhost:8080/teachers.html`
- **Schedule**: `http://localhost:8080/schedule.html`
- **Reviews**: `http://localhost:8080/reviews.html`
- **About Artist Hub**: `http://localhost:8080/about.html`

Demo Student Credentials:
- **Email**: `priyam@example.com`
- **Password**: `password123`

---

## 9. College Viva Questions & Answers

1. **Q: How is video access control enforced?**  
   *A:* In `CourseVideoService`, when `/api/classes/{id}/videos` is queried, the backend checks if the class is free or if an active enrollment exists for the student. For unpurchased paid classes, videos 2, 3, and 4 have their `videoUrl` stripped (`null`) and `isLocked` set to `true`, preventing unauthorized access even if the client-side JavaScript is inspected.
2. **Q: How does the purchase flow work?**  
   *A:* The demo purchase form sends user credentials and chosen mock payment method to `POST /api/enrollments/purchase`. The service verifies capacity, saves an `Enrollment` record with `isPaid = true` and a generated transaction ID, and immediately unlocks all lessons.
3. **Q: Why was JWT not used?**  
   *A:* For an academic mini project, introducing JWT with expiration handling, refresh tokens, and filter chains introduces unnecessary complexity. Using a clean REST controller with lightweight client-side state is stable, transparent, and easy to explain in a viva.
