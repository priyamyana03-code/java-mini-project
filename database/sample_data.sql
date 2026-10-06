-- ===================================================================
-- ARTIST HUB - Sample Data SQL Script (Optional Manual Seed)
-- ===================================================================

USE artist_hub;

-- Teachers
INSERT INTO teachers (name, bio, specialization, experience, qualification, profile_image, rating) VALUES
('Ananya Sharma', 'Award-winning visual artist specializing in vibrant watercolors, Indian folk art fusion, and contemporary oil painting techniques.', 'Watercolor & Oil Painting', '8+ Years', 'MFA from Sir J.J. Institute of Applied Art, Mumbai', 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=400&q=80', 4.9),
('Vikramaditya Roy', 'Master sketcher and illustrator focusing on hyper-realistic portraiture, human anatomy, light shading, and architectural perspectives.', 'Realistic Pencil & Charcoal Sketching', '10+ Years', 'BFA from Government College of Art & Craft, Kolkata', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=400&q=80', 4.8),
('Pooja Kulkarni', 'Passionate doodler and illustrator helping beginners overcome creative fear through mindful doodling, mandalas, and character design.', 'Creative Doodling & Line Art', '5+ Years', 'Diploma in Visual Arts, Pune', 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=400&q=80', 4.9),
('Rajesh Nair', 'Landscape artist recognized for breathtaking sunset gradients, textured pastel blending, and serene traditional Indian nature sceneries.', 'Soft Pastel & Oil Pastel Landscapes', '12+ Years', 'Master of Fine Arts, Kerala Kalamandalam', 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=400&q=80', 4.7),
('Sneha Patel', 'Experimental artist known for modern acrylic knife impasto, vibrant floral compositions, and textured canvas abstraction.', 'Contemporary Acrylic & Knife Art', '6+ Years', 'BFA from Faculty of Fine Arts, MSU Baroda', 'https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&w=400&q=80', 4.8);

-- Art Classes
INSERT INTO art_classes (name, category, description, level, mode, duration, sessions, fee, date, start_time, end_time, max_students, enrolled_students, address, city, teacher_id) VALUES
('Beginner Doodling Class', 'Doodling', 'Start your artistic journey with zero prior experience! Learn essential doodling patterns, line control, relaxing mandalas, and expressive mini-cartoons.', 'Beginner', 'Online', '2 Hours', 1, 0.0, '2026-10-18', '11:00 AM', '01:00 PM', 50, 18, 'Live Zoom Interactive Classroom', 'Online', 3),
('Beginner Painting: Acrylic on Canvas', 'Painting', 'Discover acrylic painting from scratch! Learn color mixing, gradient blending, brush control, and complete your first stunning sunset landscape on canvas.', 'Beginner', 'Online', '4 Weeks', 8, 499.0, '2026-10-20', '05:00 PM', '06:30 PM', 25, 12, 'Live Interactive Studio', 'Online', 1),
('Sketching Basics: Lines, Shapes & Shadows', 'Sketching', 'Master the foundational rules of graphite sketching. Cover pencil grades (HB to 6B), hatching, cross-hatching, perspective, and realistic 3D shadows.', 'Beginner', 'Offline', '3 Weeks', 6, 699.0, '2026-10-22', '10:00 AM', '12:00 PM', 15, 9, 'Studio Kalam, 3rd Floor, Bandra West', 'Mumbai', 2),
('Pastel Art Workshop: Sunset Landscapes', 'Pastel', 'Experience the rich, velvety texture of soft pastels. Learn fingertip blending, color layering, fixing sprays, and capturing dramatic twilight skies.', 'Intermediate', 'Offline', '2 Weeks', 4, 999.0, '2026-10-24', '03:00 PM', '05:30 PM', 12, 7, 'Artisans Cultural Guild, 12th Main, Indiranagar', 'Bengaluru', 4),
('Advanced Oil Painting & Canvas Glazing', 'Painting', 'A comprehensive masterclass on classical oil painting techniques. Learn underpainting, fat-over-lean rules, glazing layers, and gallery-grade finishes.', 'Advanced', 'Offline', '6 Weeks', 12, 1499.0, '2026-11-01', '02:00 PM', '04:30 PM', 10, 6, 'Heritage Art Loft, Lane 5, Koregaon Park', 'Pune', 1),
('Realistic Portrait Sketching Masterclass', 'Sketching', 'Deep dive into human facial anatomy, drawing expressive eyes, hair textures, skin undertones, and dramatic chiaroscuro with graphite and charcoal.', 'Advanced', 'Online', '5 Weeks', 10, 1299.0, '2026-10-28', '06:00 PM', '07:30 PM', 20, 14, 'Virtual Drawing Academy', 'Online', 2),
('Soft Pastel Floral & Botanical Art', 'Pastel', 'A gentle introduction to soft pastels for nature lovers. Capture delicate flower petals, botanical leaves, and soft pastel glow on textured paper.', 'Beginner', 'Online', '3 Weeks', 6, 599.0, '2026-10-25', '04:00 PM', '05:30 PM', 30, 15, 'Online Studio Stream', 'Online', 4),
('Intermediate Character Doodling & Comics', 'Doodling', 'Transform simple lines into dynamic cartoon characters, comic strips, funny expressions, and custom stickers. Perfect for creative storytelling.', 'Intermediate', 'Online', '4 Weeks', 8, 799.0, '2026-11-03', '07:00 PM', '08:30 PM', 25, 11, 'Digital Creative Hub', 'Online', 3),
('Modern Acrylic Knife Art', 'Painting', 'Sculpt vibrant, high-texture artwork on canvas using palette knives. Explore thick impasto strokes, abstract flowers, and modern cityscapes.', 'Intermediate', 'Offline', '3 Weeks', 6, 899.0, '2026-11-05', '11:00 AM', '01:30 PM', 12, 8, 'Color Craft Studio, CP Ring Road', 'New Delhi', 5);

-- Reviews
INSERT INTO reviews (student_name, class_name, rating, comment, review_date) VALUES
('Rohan Mehta', 'Beginner Doodling Class', 5, 'I had never drawn anything before, but Pooja ma''am made doodling so relaxing and intuitive! Highly recommend this free beginner session.', '2026-10-01'),
('Aarav Sharma', 'Beginner Painting: Acrylic on Canvas', 5, 'Ananya ma''am''s explanation of color wheels and gradients was fabulous. My first canvas looks stunning in my living room!', '2026-09-28'),
('Kavya Iyer', 'Sketching Basics: Lines, Shapes & Shadows', 5, 'Vikramaditya Roy is a wizard with pencils. His tips on graphite shading completely transformed how I sketch realistic portraits.', '2026-09-25'),
('Tanvi Deshmukh', 'Pastel Art Workshop: Sunset Landscapes', 4, 'Loved the offline studio experience in Indiranagar. The tactile feel of soft pastels and blending was therapeutic and deeply satisfying.', '2026-09-20'),
('Aditya Joshi', 'Realistic Portrait Sketching Masterclass', 5, 'The depth of human facial proportions and charcoal textures taught here is top tier. Worth every single rupee!', '2026-09-15'),
('Meera Patel', 'Modern Acrylic Knife Art', 5, 'Sneha ma''am''s knife techniques are incredible. I learned how to create rich 3D flower petals easily without fear.', '2026-09-10');
