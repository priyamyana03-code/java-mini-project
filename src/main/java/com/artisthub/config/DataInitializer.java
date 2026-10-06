package com.artisthub.config;

import com.artisthub.entity.*;
import com.artisthub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TeacherRepository teacherRepository;
    private final ArtClassRepository artClassRepository;
    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final CourseVideoRepository courseVideoRepository;
    private final QuestionRepository questionRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public DataInitializer(TeacherRepository teacherRepository,
                           ArtClassRepository artClassRepository,
                           ReviewRepository reviewRepository,
                           StudentRepository studentRepository,
                           CourseVideoRepository courseVideoRepository,
                           QuestionRepository questionRepository,
                           EnrollmentRepository enrollmentRepository) {
        this.teacherRepository = teacherRepository;
        this.artClassRepository = artClassRepository;
        this.reviewRepository = reviewRepository;
        this.studentRepository = studentRepository;
        this.courseVideoRepository = courseVideoRepository;
        this.questionRepository = questionRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println(">>> Checking Artist Hub data initialization...");

        // 1. Initialize Teachers, Classes, Reviews if empty
        if (teacherRepository.count() == 0) {
            System.out.println(">>> Initializing Teachers, Classes and Reviews...");
            initCoreData();
        }

        // 2. Initialize Students for login demo
        if (studentRepository.count() == 0) {
            System.out.println(">>> Initializing Sample Students...");
            initStudents();
        }

        // 3. Initialize 4 Course Videos per Art Class
        if (courseVideoRepository.count() == 0) {
            System.out.println(">>> Initializing 4 Course Videos per class...");
            initCourseVideos();
        }

        // 4. Initialize Questions & Answers
        if (questionRepository.count() == 0) {
            System.out.println(">>> Initializing Course Questions & Answers...");
            initQuestions();
        }

        System.out.println(">>> Artist Hub data check completed successfully!");
    }

    private void initCoreData() {
        // Teachers
        Teacher t1 = new Teacher(
                "Ananya Sharma",
                "Award-winning visual artist specializing in vibrant watercolors, Indian folk art fusion, and contemporary oil painting techniques.",
                "Watercolor & Oil Painting",
                "8+ Years",
                "MFA from Sir J.J. Institute of Applied Art, Mumbai",
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=400&q=80",
                4.9
        );

        Teacher t2 = new Teacher(
                "Vikramaditya Roy",
                "Master sketcher and illustrator focusing on hyper-realistic portraiture, human anatomy, light shading, and architectural perspectives.",
                "Realistic Pencil & Charcoal Sketching",
                "10+ Years",
                "BFA from Government College of Art & Craft, Kolkata",
                "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=400&q=80",
                4.8
        );

        Teacher t3 = new Teacher(
                "Pooja Kulkarni",
                "Passionate doodler and illustrator helping beginners overcome creative fear through mindful doodling, mandalas, and character design.",
                "Creative Doodling & Line Art",
                "5+ Years",
                "Diploma in Visual Arts, Pune",
                "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=400&q=80",
                4.9
        );

        Teacher t4 = new Teacher(
                "Rajesh Nair",
                "Landscape artist recognized for breathtaking sunset gradients, textured pastel blending, and serene traditional Indian nature sceneries.",
                "Soft Pastel & Oil Pastel Landscapes",
                "12+ Years",
                "Master of Fine Arts, Kerala Kalamandalam",
                "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=400&q=80",
                4.7
        );

        Teacher t5 = new Teacher(
                "Sneha Patel",
                "Experimental artist known for modern acrylic knife impasto, vibrant floral compositions, and textured canvas abstraction.",
                "Contemporary Acrylic & Knife Art",
                "6+ Years",
                "BFA from Faculty of Fine Arts, MSU Baroda",
                "https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&w=400&q=80",
                4.8
        );

        teacherRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));

        // Art Classes
        ArtClass c1 = new ArtClass(
                "Beginner Doodling Class",
                "Doodling",
                "Start your artistic journey with zero prior experience! Learn essential doodling patterns, line control, relaxing mandalas, and expressive mini-cartoons.",
                "Beginner",
                "Online",
                "2 Hours",
                1,
                0.0, // FREE
                "2026-10-18",
                "11:00 AM",
                "01:00 PM",
                50,
                18,
                "Live Zoom Interactive Classroom",
                "Online",
                t3
        );

        ArtClass c2 = new ArtClass(
                "Beginner Painting: Acrylic on Canvas",
                "Painting",
                "Discover acrylic painting from scratch! Learn color mixing, gradient blending, brush control, and complete your first stunning sunset landscape on canvas.",
                "Beginner",
                "Online",
                "4 Weeks",
                8,
                499.0,
                "2026-10-20",
                "05:00 PM",
                "06:30 PM",
                25,
                12,
                "Live Interactive Studio",
                "Online",
                t1
        );

        ArtClass c3 = new ArtClass(
                "Sketching Basics: Lines, Shapes & Shadows",
                "Sketching",
                "Master the foundational rules of graphite sketching. Cover pencil grades (HB to 6B), hatching, cross-hatching, perspective, and realistic 3D shadows.",
                "Beginner",
                "Offline",
                "3 Weeks",
                6,
                699.0,
                "2026-10-22",
                "10:00 AM",
                "12:00 PM",
                15,
                9,
                "Studio Kalam, 3rd Floor, Bandra West",
                "Mumbai",
                t2
        );

        ArtClass c4 = new ArtClass(
                "Pastel Art Workshop: Sunset Landscapes",
                "Pastel",
                "Experience the rich, velvety texture of soft pastels. Learn fingertip blending, color layering, fixing sprays, and capturing dramatic twilight skies.",
                "Intermediate",
                "Offline",
                "2 Weeks",
                4,
                999.0,
                "2026-10-24",
                "03:00 PM",
                "05:30 PM",
                12,
                7,
                "Artisans Cultural Guild, 12th Main, Indiranagar",
                "Bengaluru",
                t4
        );

        ArtClass c5 = new ArtClass(
                "Advanced Oil Painting & Canvas Glazing",
                "Painting",
                "A comprehensive masterclass on classical oil painting techniques. Learn underpainting, fat-over-lean rules, glazing layers, and gallery-grade finishes.",
                "Advanced",
                "Offline",
                "6 Weeks",
                12,
                1499.0,
                "2026-11-01",
                "02:00 PM",
                "04:30 PM",
                10,
                6,
                "Heritage Art Loft, Lane 5, Koregaon Park",
                "Pune",
                t1
        );

        ArtClass c6 = new ArtClass(
                "Realistic Portrait Sketching Masterclass",
                "Sketching",
                "Deep dive into human facial anatomy, drawing expressive eyes, hair textures, skin undertones, and dramatic chiaroscuro with graphite and charcoal.",
                "Advanced",
                "Online",
                "5 Weeks",
                10,
                1299.0,
                "2026-10-28",
                "06:00 PM",
                "07:30 PM",
                20,
                14,
                "Virtual Drawing Academy",
                "Online",
                t2
        );

        ArtClass c7 = new ArtClass(
                "Soft Pastel Floral & Botanical Art",
                "Pastel",
                "A gentle introduction to soft pastels for nature lovers. Capture delicate flower petals, botanical leaves, and soft pastel glow on textured paper.",
                "Beginner",
                "Online",
                "3 Weeks",
                6,
                599.0,
                "2026-10-25",
                "04:00 PM",
                "05:30 PM",
                30,
                15,
                "Online Studio Stream",
                "Online",
                t4
        );

        ArtClass c8 = new ArtClass(
                "Intermediate Character Doodling & Comics",
                "Doodling",
                "Transform simple lines into dynamic cartoon characters, comic strips, funny expressions, and custom stickers. Perfect for creative storytelling.",
                "Intermediate",
                "Online",
                "4 Weeks",
                8,
                799.0,
                "2026-11-03",
                "07:00 PM",
                "08:30 PM",
                25,
                11,
                "Digital Creative Hub",
                "Online",
                t3
        );

        ArtClass c9 = new ArtClass(
                "Modern Acrylic Knife Art",
                "Painting",
                "Sculpt vibrant, high-texture artwork on canvas using palette knives. Explore thick impasto strokes, abstract flowers, and modern cityscapes.",
                "Intermediate",
                "Offline",
                "3 Weeks",
                6,
                899.0,
                "2026-11-05",
                "11:00 AM",
                "01:30 PM",
                12,
                8,
                "Color Craft Studio, CP Ring Road",
                "New Delhi",
                t5
        );

        artClassRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9));

        // Sample Reviews
        Review r1 = new Review(
                "Rohan Mehta",
                "Beginner Doodling Class",
                5,
                "I had never drawn anything before, but Pooja ma'am made doodling so relaxing and intuitive! Highly recommend this free beginner session.",
                LocalDate.now().minusDays(3)
        );

        Review r2 = new Review(
                "Aarav Sharma",
                "Beginner Painting: Acrylic on Canvas",
                5,
                "Ananya ma'am's explanation of color wheels and gradients was fabulous. My first canvas looks stunning in my living room!",
                LocalDate.now().minusDays(7)
        );

        Review r3 = new Review(
                "Kavya Iyer",
                "Sketching Basics: Lines, Shapes & Shadows",
                5,
                "Vikramaditya Roy is a wizard with pencils. His tips on graphite shading completely transformed how I sketch realistic portraits.",
                LocalDate.now().minusDays(10)
        );

        Review r4 = new Review(
                "Tanvi Deshmukh",
                "Pastel Art Workshop: Sunset Landscapes",
                4,
                "Loved the offline studio experience in Indiranagar. The tactile feel of soft pastels and blending was therapeutic and deeply satisfying.",
                LocalDate.now().minusDays(14)
        );

        Review r5 = new Review(
                "Aditya Joshi",
                "Realistic Portrait Sketching Masterclass",
                5,
                "The depth of human facial proportions and charcoal textures taught here is top tier. Worth every single rupee!",
                LocalDate.now().minusDays(20)
        );

        Review r6 = new Review(
                "Meera Patel",
                "Modern Acrylic Knife Art",
                5,
                "Sneha ma'am's knife techniques are incredible. I learned how to create rich 3D flower petals easily without fear.",
                LocalDate.now().minusDays(25)
        );

        reviewRepository.saveAll(Arrays.asList(r1, r2, r3, r4, r5, r6));
    }

    private void initStudents() {
        Student s1 = new Student("Priyam Yana", "priyam@example.com", "+91 9876543210", "password123");
        Student s2 = new Student("Rohan Mehta", "rohan@example.com", "+91 9811223344", "password123");
        studentRepository.saveAll(Arrays.asList(s1, s2));
    }

    private void initCourseVideos() {
        List<ArtClass> classes = artClassRepository.findAll();
        List<CourseVideo> allVideos = new ArrayList<>();

        // Standard sample HTML5 video URLs that play cleanly in all browsers
        // Easy for user to update via database or REST API later!
        String v1 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        String v2 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
        String v3 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4";
        String v4 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4";

        for (ArtClass c : classes) {
            String name = c.getName();
            String cat = c.getCategory() != null ? c.getCategory() : "Art";

            // Lesson 1 - Introduction
            allVideos.add(new CourseVideo(
                    "Lesson 1 - Introduction to " + cat + " & Core Materials",
                    "Welcome to " + name + "! In this lesson, we explore essential art supplies, work area preparation, and fundamental posture and strokes.",
                    v1,
                    1,
                    "15 Mins",
                    c
            ));

            // Lesson 2 - Basic Techniques
            allVideos.add(new CourseVideo(
                    "Lesson 2 - Essential Techniques & Hands-on Exercises",
                    "Deep dive into foundational techniques for " + cat + ". Learn controlled pressure, consistent transitions, and practice drills.",
                    v2,
                    2,
                    "22 Mins",
                    c
            ));

            // Lesson 3 - Advanced Methods / Color & Texture
            allVideos.add(new CourseVideo(
                    "Lesson 3 - Intermediate Blending & Composition Rules",
                    "Level up with composition framing, perspective, tonal values, and delicate texturing suitable for " + name + ".",
                    v3,
                    3,
                    "28 Mins",
                    c
            ));

            // Lesson 4 - Final Project
            allVideos.add(new CourseVideo(
                    "Lesson 4 - Final Project: Complete Artwork from Scratch",
                    "Put everything together! Step-by-step guided creation of your signature final project piece from start to varnishing/fixing.",
                    v4,
                    4,
                    "35 Mins",
                    c
            ));
        }

        courseVideoRepository.saveAll(allVideos);
        System.out.println(">>> Seeded " + allVideos.size() + " video lessons (4 per course) across " + classes.size() + " classes!");
    }

    private void initQuestions() {
        List<ArtClass> classes = artClassRepository.findAll();
        if (classes.isEmpty()) return;

        List<Question> questions = new ArrayList<>();

        for (ArtClass c : classes) {
            String cat = c.getCategory() != null ? c.getCategory() : "Art";

            if ("Painting".equalsIgnoreCase(cat)) {
                questions.add(new Question(
                        "Kavya Iyer",
                        "kavya@example.com",
                        "How do I prevent my acrylic colors from drying too fast on the palette while blending?",
                        "Great question, Kavya! Lightly mist your palette with a fine water spray or use a DIY stay-wet palette with parchment paper.",
                        LocalDate.now().minusDays(2),
                        c
                ));
                questions.add(new Question(
                        "Arjun Das",
                        "arjun@example.com",
                        "Can I paint on standard 300 GSM watercolor paper instead of canvas?",
                        "Yes, absolutely! Just apply one thin layer of white acrylic gesso beforehand so the paper fibers don't soak up too much paint.",
                        LocalDate.now().minusDays(5),
                        c
                ));
            } else if ("Sketching".equalsIgnoreCase(cat)) {
                questions.add(new Question(
                        "Rohan Mehta",
                        "rohan@example.com",
                        "When should I use a 2B pencil versus a 6B pencil for portraits?",
                        "Use 2B for initial outlines and gentle facial undertones. Save 4B and 6B for deep darks like pupils, eyelashes, and hair shadows.",
                        LocalDate.now().minusDays(1),
                        c
                ));
                questions.add(new Question(
                        "Neha Gupta",
                        "neha@example.com",
                        "Is blending with fingers recommended, or should I only use blending stumps?",
                        "Avoid fingers because natural skin oils smudge graphite unevenly. A paper tortillon or blending stump gives much smoother gradations.",
                        LocalDate.now().minusDays(4),
                        c
                ));
            } else if ("Doodling".equalsIgnoreCase(cat)) {
                questions.add(new Question(
                        "Aditya Joshi",
                        "aditya@example.com",
                        "What fineliner pen tip size do you recommend for beginner mandala borders?",
                        "A 0.5mm black pigment fineliner is ideal for outer borders, and a 0.2mm or 0.3mm is perfect for intricate interior floral fills.",
                        LocalDate.now().minusDays(2),
                        c
                ));
                questions.add(new Question(
                        "Snehal Patil",
                        "snehal@example.com",
                        "How do I overcome the fear of making a mistake with permanent black ink?",
                        "Embrace the 'no mistake' rule in doodling! Any accidental line can be turned into a leaf, pattern texture, or whimsical character feature.",
                        LocalDate.now().minusDays(6),
                        c
                ));
            } else { // Pastel
                questions.add(new Question(
                        "Tanvi Deshmukh",
                        "tanvi@example.com",
                        "Which side of the pastel paper should I draw on: the smooth side or textured side?",
                        "Always use the textured side with 'tooth'! The paper tooth holds multiple layers of pastel pigment without crumbling.",
                        LocalDate.now().minusDays(3),
                        c
                ));
                questions.add(new Question(
                        "Vikram Sharma",
                        "vikram@example.com",
                        "Does hairspray work as a substitute for professional art fixative spray?",
                        "It's better to avoid hairspray as it can yellow paper over time. A dedicated workable pastel fixative preserves colors accurately.",
                        LocalDate.now().minusDays(7),
                        c
                ));
            }
        }

        questionRepository.saveAll(questions);
        System.out.println(">>> Seeded " + questions.size() + " course questions & mentor answers!");
    }
}
