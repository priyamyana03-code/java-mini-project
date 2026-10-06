// ===================================================================
// ARTIST HUB - Classes Page Logic & API Integration
// ===================================================================

let allClasses = [];

async function loadClasses() {
    const container = document.getElementById('classesGrid');
    if (!container) return;

    // Read current filter values
    const searchVal = document.getElementById('searchInput')?.value.trim() || '';
    const categoryVal = document.getElementById('categoryFilter')?.value || '';
    const levelVal = document.getElementById('levelFilter')?.value || '';
    const modeVal = document.getElementById('modeFilter')?.value || '';
    const priceVal = document.getElementById('priceFilter')?.value || '';

    // Build URL with query params
    const params = new URLSearchParams();
    if (searchVal) params.append('search', searchVal);
    if (categoryVal) params.append('category', categoryVal);
    if (levelVal) params.append('level', levelVal);
    if (modeVal) params.append('mode', modeVal);
    if (priceVal) params.append('maxFee', priceVal);

    try {
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <div class="spinner-border text-primary" role="status"></div>
                <p class="mt-2 text-muted">Loading amazing art classes...</p>
            </div>
        `;

        const response = await fetch(`${API_BASE}/classes?${params.toString()}`);
        if (!response.ok) throw new Error('Failed to fetch classes');

        const classes = await response.json();
        allClasses = classes;
        renderClasses(classes, container);

        // Update count badge
        const countBadge = document.getElementById('classCountBadge');
        if (countBadge) {
            countBadge.textContent = `${classes.length} Classes Available`;
        }
    } catch (error) {
        console.error('Error fetching classes:', error);
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <i class="fa-solid fa-triangle-exclamation text-warning fa-3x mb-3"></i>
                <h5>Unable to load art classes</h5>
                <p class="text-muted">Please check if the backend server is running on port 8080.</p>
                <button class="btn btn-outline-custom mt-2" onclick="loadClasses()">Retry</button>
            </div>
        `;
    }
}

function renderClasses(classes, container) {
    if (!classes || classes.length === 0) {
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <i class="fa-solid fa-paintbrush fa-3x text-muted mb-3 opacity-50"></i>
                <h4>No classes found matching your criteria</h4>
                <p class="text-muted">Try clearing some filters or searching for something else.</p>
                <button class="btn btn-primary-custom btn-sm mt-2" onclick="resetFilters()">Reset All Filters</button>
            </div>
        `;
        return;
    }

    container.innerHTML = classes.map(cls => {
        const teacherName = cls.teacher ? cls.teacher.name : 'Master Instructor';
        const teacherImg = cls.teacher && cls.teacher.profileImage ? cls.teacher.profileImage : 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=150&q=80';
        const availableSeats = cls.availableSeats !== undefined ? cls.availableSeats : Math.max(0, (cls.maxStudents || 0) - (cls.enrolledStudents || 0));
        const isFree = !cls.fee || Number(cls.fee) === 0;

        return `
            <div class="col-lg-4 col-md-6 mb-4">
                <div class="art-card">
                    <div class="card-img-holder">
                        <img src="${getClassCategoryImg(cls.category)}" alt="${cls.name}">
                        <div class="card-badge-top">${getCategoryBadge(cls.category)}</div>
                        <div class="card-mode-top">${getModeBadge(cls.mode)}</div>
                    </div>
                    <div class="art-card-body">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <span class="badge bg-light text-dark border">${cls.level || 'All Levels'}</span>
                            <span class="text-warning small"><i class="fa-solid fa-star me-1"></i>${cls.teacher && cls.teacher.rating ? cls.teacher.rating : '4.9'}</span>
                        </div>
                        <h5 class="class-title">${cls.name}</h5>
                        <p class="class-desc">${cls.description || ''}</p>
                        
                        <div class="teacher-snippet">
                            <img src="${teacherImg}" alt="${teacherName}">
                            <div>
                                <p class="teacher-name">${teacherName}</p>
                                <p class="teacher-spec">${cls.teacher ? cls.teacher.specialization : 'Art Mentor'}</p>
                            </div>
                        </div>

                        <div class="class-meta">
                            <span><i class="fa-regular fa-calendar text-primary"></i> ${cls.date || 'Upcoming'}</span>
                            <span><i class="fa-regular fa-clock text-primary"></i> ${cls.duration || 'Session'}</span>
                            <span>${getSeatsBadge(availableSeats, cls.maxStudents)}</span>
                        </div>

                        <div class="card-bottom-row">
                            <div>
                                ${formatFee(cls.fee)}
                            </div>
                            <div class="d-flex gap-2">
                                <a href="course-detail.html?id=${cls.id}" class="btn btn-sm btn-outline-primary rounded-pill">
                                    <i class="fa-solid fa-play-circle me-1"></i>Lessons
                                </a>
                                <button class="btn btn-sm btn-primary-custom" 
                                    ${availableSeats <= 0 ? 'disabled' : ''} 
                                    onclick="openEnrollmentModal(${cls.id}, '${cls.name.replace(/'/g, "\\'")}', ${cls.fee})">
                                    ${availableSeats <= 0 ? 'Full' : (isFree ? 'Join Free' : 'Join')}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }).join('');
}

// Show detailed modal for selected class
async function viewClassDetails(id) {
    try {
        const res = await fetch(`${API_BASE}/classes/${id}`);
        if (!res.ok) throw new Error('Class not found');
        const cls = await res.json();

        const modalEl = document.getElementById('classDetailsModal');
        if (!modalEl) return;

        const availableSeats = cls.availableSeats !== undefined ? cls.availableSeats : Math.max(0, (cls.maxStudents || 0) - (cls.enrolledStudents || 0));
        const isOffline = (cls.mode || '').toLowerCase() === 'offline';

        document.getElementById('modalClassName').textContent = cls.name;
        document.getElementById('modalClassCategory').innerHTML = `${getCategoryBadge(cls.category)} ${getModeBadge(cls.mode)} <span class="badge bg-secondary ms-1">${cls.level}</span>`;
        document.getElementById('modalClassDesc').textContent = cls.description || 'No description provided.';
        document.getElementById('modalClassFee').innerHTML = formatFee(cls.fee);
        document.getElementById('modalClassDuration').textContent = cls.duration || 'Flexible';
        document.getElementById('modalClassSessions').textContent = `${cls.sessions || 1} Sessions`;
        document.getElementById('modalClassDateTime').textContent = `${cls.date || 'TBD'} (${cls.startTime || ''} - ${cls.endTime || ''})`;
        document.getElementById('modalClassSeats').innerHTML = `${getSeatsBadge(availableSeats, cls.maxStudents)} (${cls.enrolledStudents || 0} / ${cls.maxStudents || 0} Enrolled)`;

        // Teacher Info
        if (cls.teacher) {
            document.getElementById('modalTeacherImg').src = cls.teacher.profileImage || '';
            document.getElementById('modalTeacherName').textContent = cls.teacher.name;
            document.getElementById('modalTeacherSpec').textContent = cls.teacher.specialization;
            document.getElementById('modalTeacherExp').textContent = `${cls.teacher.experience} • ${cls.teacher.qualification}`;
        }

        // Course Lessons button in modal
        const lessonsBtn = document.getElementById('modalCourseLessonsBtn');
        if (lessonsBtn) {
            lessonsBtn.href = `course-detail.html?id=${cls.id}`;
        }

        // Location Info
        const locContainer = document.getElementById('modalLocationContainer');
        if (locContainer) {
            if (isOffline) {
                locContainer.innerHTML = `
                    <div class="alert alert-light border d-flex align-items-start gap-3 mt-3 mb-0">
                        <i class="fa-solid fa-map-location-dot text-danger fa-2x mt-1"></i>
                        <div>
                            <strong class="d-block text-dark">Offline Studio Address</strong>
                            <p class="mb-1 text-muted small">${cls.address || 'Studio Location'}</p>
                            <span class="badge bg-dark">${cls.city || 'India'}</span>
                            <span class="badge bg-outline-secondary border text-muted ms-1"><i class="fa-solid fa-route me-1"></i>View Location</span>
                        </div>
                    </div>
                `;
            } else {
                locContainer.innerHTML = `
                    <div class="alert alert-light border d-flex align-items-center gap-3 mt-3 mb-0">
                        <i class="fa-solid fa-video text-primary fa-2x"></i>
                        <div>
                            <strong class="d-block text-dark">Online Live Interactive Studio</strong>
                            <p class="mb-0 text-muted small">Live interactive room link provided upon enrollment confirmation. Attend from anywhere!</p>
                        </div>
                    </div>
                `;
            }
        }

        // Join button inside modal
        const joinBtn = document.getElementById('modalJoinBtn');
        if (joinBtn) {
            if (availableSeats <= 0) {
                joinBtn.disabled = true;
                joinBtn.textContent = 'Class Fully Booked';
            } else {
                joinBtn.disabled = false;
                joinBtn.innerHTML = `<i class="fa-solid fa-paper-plane me-1"></i> ${cls.fee === 0 ? 'Join Free Class' : 'Enroll Now'}`;
                joinBtn.onclick = () => {
                    const detailModal = bootstrap.Modal.getInstance(modalEl);
                    if (detailModal) detailModal.hide();
                    openEnrollmentModal(cls.id, cls.name, cls.fee);
                };
            }
        }

        const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
        modal.show();
    } catch (err) {
        console.error('Error viewing class details:', err);
        showNotification('Unable to fetch class details.', 'error');
    }
}

function resetFilters() {
    const s = document.getElementById('searchInput');
    const c = document.getElementById('categoryFilter');
    const l = document.getElementById('levelFilter');
    const m = document.getElementById('modeFilter');
    const p = document.getElementById('priceFilter');
    if (s) s.value = '';
    if (c) c.value = '';
    if (l) l.value = '';
    if (m) m.value = '';
    if (p) p.value = '';
    loadClasses();
}

// Bind events on DOM ready
document.addEventListener('DOMContentLoaded', () => {
    // If on classes.html
    if (document.getElementById('classesGrid')) {
        loadClasses();

        // Bind filter change events
        ['categoryFilter', 'levelFilter', 'modeFilter', 'priceFilter'].forEach(id => {
            const el = document.getElementById(id);
            if (el) el.addEventListener('change', loadClasses);
        });

        // Search debouncing or input
        const searchInput = document.getElementById('searchInput');
        if (searchInput) {
            let timeout = null;
            searchInput.addEventListener('input', () => {
                clearTimeout(timeout);
                timeout = setTimeout(loadClasses, 350);
            });
        }
    }
});
