// ===================================================================
// ARTIST HUB - Enrollment Modal & API Integration
// ===================================================================

let currentEnrollmentClassId = null;

// Open enrollment modal for a specific class ID
function openEnrollmentModal(classId, className, classFee) {
    currentEnrollmentClassId = classId;

    const modalEl = document.getElementById('enrollmentModal');
    if (!modalEl) return;

    // Reset form
    const form = document.getElementById('enrollmentForm');
    if (form) form.reset();

    // Populate modal title & class info
    const titleEl = document.getElementById('enrollmentModalClassTitle');
    const feeEl = document.getElementById('enrollmentModalClassFee');
    const classIdInput = document.getElementById('enrollmentClassId');

    if (titleEl) titleEl.textContent = className || 'Art Class';
    if (feeEl) {
        if (!classFee || Number(classFee) === 0) {
            feeEl.innerHTML = '<span class="badge bg-success">FREE Session</span>';
        } else {
            feeEl.innerHTML = `<span class="badge bg-primary">Fee: ₹${Number(classFee).toLocaleString('en-IN')}</span>`;
        }
    }
    if (classIdInput) classIdInput.value = classId;

    const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
    modal.show();
}

// Function specifically to trigger the Free Doodling Class - leads directly to the free course
async function joinFreeDoodlingClass() {
    try {
        const res = await fetch(`${API_BASE}/classes`);
        if (res.ok) {
            const classes = await res.json();
            const freeClass = classes.find(c => c.fee === 0 || (c.name && c.name.toLowerCase().includes('doodling')));
            if (freeClass) {
                window.location.href = `course-detail.html?id=${freeClass.id}`;
                return;
            }
        }
    } catch (err) {
        console.error('Error finding free class:', err);
    }
    // Fallback: direct to course-detail.html for ID 1
    window.location.href = 'course-detail.html?id=1';
}

// Submit enrollment form handler
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('enrollmentForm');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const nameInput = document.getElementById('studentName');
        const emailInput = document.getElementById('studentEmail');
        const phoneInput = document.getElementById('studentPhone');
        const classIdInput = document.getElementById('enrollmentClassId');
        const submitBtn = document.getElementById('enrollmentSubmitBtn');

        const studentName = nameInput ? nameInput.value.trim() : '';
        const studentEmail = emailInput ? emailInput.value.trim() : '';
        const studentPhone = phoneInput ? phoneInput.value.trim() : '';
        const classId = classIdInput ? Number(classIdInput.value) : currentEnrollmentClassId;

        // Front-end validation
        if (!studentName) {
            showNotification('Please enter your full name.', 'error');
            return;
        }
        if (!studentEmail || !studentEmail.includes('@')) {
            showNotification('Please enter a valid email address.', 'error');
            return;
        }
        if (!studentPhone) {
            showNotification('Please enter your phone number.', 'error');
            return;
        }
        if (!classId) {
            showNotification('No class selected for enrollment.', 'error');
            return;
        }

        // Disable button during submit
        if (submitBtn) {
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Enrolling...';
        }

        try {
            const response = await fetch(`${API_BASE}/enrollments`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    studentName: studentName,
                    studentEmail: studentEmail,
                    studentPhone: studentPhone,
                    classId: classId
                })
            });

            const result = await response.json();

            if (response.ok) {
                // Close modal
                const modalEl = document.getElementById('enrollmentModal');
                const modal = bootstrap.Modal.getInstance(modalEl);
                if (modal) modal.hide();

                // Reset form
                form.reset();

                // Show success notification
                showNotification(result.message || 'Enrollment successful! Welcome to Artist Hub.', 'success');

                // If on classes or schedule page, refresh data to update available seats
                if (typeof loadClasses === 'function') {
                    loadClasses();
                }
                if (typeof loadSchedule === 'function') {
                    loadSchedule();
                }
            } else {
                showNotification(result.error || 'Enrollment failed. Please check your details.', 'error');
            }
        } catch (error) {
            console.error('Enrollment error:', error);
            showNotification('Server communication error. Please ensure backend is running.', 'error');
        } finally {
            if (submitBtn) {
                submitBtn.disabled = false;
                submitBtn.innerHTML = '<i class="fa-solid fa-check me-2"></i>Confirm Enrollment';
            }
        }
    });
});
