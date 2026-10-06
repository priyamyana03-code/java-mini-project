// ===================================================================
// ARTIST HUB - Teachers Page Logic & API Integration
// ===================================================================

let allTeachers = [];

async function loadTeachers() {
    const container = document.getElementById('teachersGrid');
    if (!container) return;

    try {
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <div class="spinner-border text-primary" role="status"></div>
                <p class="mt-2 text-muted">Loading our talented art teachers...</p>
            </div>
        `;

        const response = await fetch(`${API_BASE}/teachers`);
        if (!response.ok) throw new Error('Failed to fetch teachers');

        const teachers = await response.json();
        allTeachers = teachers;
        renderTeachers(teachers, container);
    } catch (error) {
        console.error('Error fetching teachers:', error);
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <i class="fa-solid fa-triangle-exclamation text-warning fa-3x mb-3"></i>
                <h5>Unable to load teachers</h5>
                <p class="text-muted">Please ensure the backend application is running on port 8080.</p>
                <button class="btn btn-outline-custom mt-2" onclick="loadTeachers()">Retry</button>
            </div>
        `;
    }
}

function renderTeachers(teachers, container) {
    if (!teachers || teachers.length === 0) {
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <p class="text-muted">No teachers found.</p>
            </div>
        `;
        return;
    }

    container.innerHTML = teachers.map(t => {
        const rating = t.rating ? t.rating.toFixed(1) : '5.0';
        const img = t.profileImage || 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=300&q=80';

        return `
            <div class="col-lg-4 col-md-6 mb-4">
                <div class="teacher-card">
                    <div class="teacher-avatar-wrapper">
                        <img src="${img}" alt="${t.name}">
                    </div>
                    <div class="star-rating mb-2">
                        ${getStarRatingHtml(t.rating)}
                        <span class="ms-1 fw-bold text-dark small">${rating}</span>
                    </div>
                    <h4 class="teacher-card-name">${t.name}</h4>
                    <p class="teacher-card-spec">${t.specialization}</p>
                    
                    <div class="teacher-meta-pill">
                        <i class="fa-solid fa-graduation-cap text-primary"></i> ${t.experience} Experience
                    </div>

                    <p class="teacher-card-bio">${t.bio || ''}</p>
                    
                    <div class="mt-3">
                        <button class="btn btn-sm btn-outline-custom" onclick="viewTeacherProfile(${t.id})">
                            <i class="fa-solid fa-user me-1"></i>View Profile & Classes
                        </button>
                    </div>
                </div>
            </div>
        `;
    }).join('');
}

async function viewTeacherProfile(id) {
    try {
        const res = await fetch(`${API_BASE}/teachers/${id}`);
        if (!res.ok) throw new Error('Teacher not found');
        const teacher = await res.json();

        const modalEl = document.getElementById('teacherModal');
        if (!modalEl) return;

        document.getElementById('teacherModalImg').src = teacher.profileImage || '';
        document.getElementById('teacherModalName').textContent = teacher.name;
        document.getElementById('teacherModalSpec').textContent = teacher.specialization;
        document.getElementById('teacherModalExp').textContent = teacher.experience;
        document.getElementById('teacherModalQual').textContent = teacher.qualification || 'Certified Art Master';
        document.getElementById('teacherModalRating').innerHTML = `${getStarRatingHtml(teacher.rating)} <span class="fw-bold ms-1">(${teacher.rating})</span>`;
        document.getElementById('teacherModalBio').textContent = teacher.bio || '';

        // Render classes taught by this teacher
        const classesListEl = document.getElementById('teacherModalClassesList');
        if (classesListEl) {
            if (teacher.artClasses && teacher.artClasses.length > 0) {
                classesListEl.innerHTML = teacher.artClasses.map(c => `
                    <div class="list-group-item d-flex justify-content-between align-items-center p-3 border-0 bg-light rounded-3 mb-2">
                        <div>
                            <h6 class="mb-1 fw-bold text-dark">${c.name}</h6>
                            <span class="small text-muted">${c.level} • ${c.mode} • ${c.duration}</span>
                        </div>
                        <div class="text-end">
                            <div class="fw-bold mb-1">${c.fee === 0 ? '<span class="text-success">FREE</span>' : '₹' + c.fee}</div>
                            <button class="btn btn-sm btn-primary-custom" onclick="closeTeacherModalAndEnroll(${c.id}, '${c.name.replace(/'/g, "\\'")}', ${c.fee})">
                                Join Class
                            </button>
                        </div>
                    </div>
                `).join('');
            } else {
                classesListEl.innerHTML = `<p class="text-muted small">No scheduled classes at this moment.</p>`;
            }
        }

        const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
        modal.show();
    } catch (err) {
        console.error('Error fetching teacher profile:', err);
        showNotification('Unable to fetch teacher details.', 'error');
    }
}

function closeTeacherModalAndEnroll(classId, className, classFee) {
    const teacherModalEl = document.getElementById('teacherModal');
    const teacherModal = bootstrap.Modal.getInstance(teacherModalEl);
    if (teacherModal) teacherModal.hide();

    openEnrollmentModal(classId, className, classFee);
}

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('teachersGrid')) {
        loadTeachers();
    }
});
