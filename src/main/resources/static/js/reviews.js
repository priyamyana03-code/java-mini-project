// ===================================================================
// ARTIST HUB - Reviews Page Logic & API Integration
// ===================================================================

let selectedStarRating = 5;

async function loadReviews() {
    const container = document.getElementById('reviewsGrid');
    if (!container) return;

    try {
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <div class="spinner-border text-primary" role="status"></div>
                <p class="mt-2 text-muted">Loading student reviews...</p>
            </div>
        `;

        const response = await fetch(`${API_BASE}/reviews`);
        if (!response.ok) throw new Error('Failed to fetch reviews');

        const reviews = await response.json();
        renderReviews(reviews, container);
        updateReviewStats(reviews);
    } catch (error) {
        console.error('Error fetching reviews:', error);
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <i class="fa-solid fa-triangle-exclamation text-warning fa-3x mb-3"></i>
                <h5>Unable to load reviews</h5>
                <p class="text-muted">Make sure the Spring Boot backend is active.</p>
                <button class="btn btn-outline-custom mt-2" onclick="loadReviews()">Retry</button>
            </div>
        `;
    }
}

function updateReviewStats(reviews) {
    const avgEl = document.getElementById('avgRatingValue');
    const totalEl = document.getElementById('totalReviewsCount');
    const starsEl = document.getElementById('avgRatingStars');

    if (!reviews || reviews.length === 0) {
        if (avgEl) avgEl.textContent = '5.0';
        if (totalEl) totalEl.textContent = '0 Reviews';
        return;
    }

    const total = reviews.length;
    const sum = reviews.reduce((acc, r) => acc + (Number(r.rating) || 5), 0);
    const avg = (sum / total).toFixed(1);

    if (avgEl) avgEl.textContent = avg;
    if (totalEl) totalEl.textContent = `${total} Student Reviews`;
    if (starsEl) starsEl.innerHTML = getStarRatingHtml(Math.round(avg));
}

function renderReviews(reviews, container) {
    if (!reviews || reviews.length === 0) {
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <p class="text-muted">No reviews yet. Be the first student to review!</p>
            </div>
        `;
        return;
    }

    container.innerHTML = reviews.map(r => {
        const dateStr = r.reviewDate ? new Date(r.reviewDate).toLocaleDateString('en-IN', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        }) : 'Recent';

        return `
            <div class="col-lg-4 col-md-6 mb-4">
                <div class="review-card">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <div class="star-rating">${getStarRatingHtml(r.rating)}</div>
                        <span class="review-date">${dateStr}</span>
                    </div>
                    <p class="review-comment">"${r.comment}"</p>
                    <div class="d-flex align-items-center gap-2 mt-auto pt-2 border-top">
                        <div class="rounded-circle bg-light d-flex align-items-center justify-content-center text-primary fw-bold" style="width: 38px; height: 38px; border: 1px solid #ede8e1;">
                            ${r.studentName ? r.studentName.charAt(0).toUpperCase() : 'S'}
                        </div>
                        <div>
                            <p class="review-author">${r.studentName}</p>
                            <p class="review-class">${r.className}</p>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }).join('');
}

// Populate classes dropdown in Review form
async function populateReviewClassesDropdown() {
    const dropdown = document.getElementById('reviewClassName');
    if (!dropdown) return;

    try {
        const res = await fetch(`${API_BASE}/classes`);
        if (res.ok) {
            const classes = await res.json();
            dropdown.innerHTML = '<option value="">-- Select Class Taken --</option>' + 
                classes.map(c => `<option value="${c.name}">${c.name}</option>`).join('');
        }
    } catch (e) {
        console.error('Error populating review classes:', e);
    }
}

// Star rating picker interactivity
function setupStarRatingPicker() {
    const stars = document.querySelectorAll('.star-rating-select i');
    if (!stars || stars.length === 0) return;

    stars.forEach(star => {
        star.addEventListener('click', () => {
            const val = parseInt(star.getAttribute('data-value'));
            selectedStarRating = val;
            updateStarPickerVisuals(val);
        });
    });
}

function updateStarPickerVisuals(val) {
    const stars = document.querySelectorAll('.star-rating-select i');
    stars.forEach(s => {
        const sVal = parseInt(s.getAttribute('data-value'));
        if (sVal <= val) {
            s.className = 'fa-solid fa-star active';
        } else {
            s.className = 'fa-regular fa-star';
        }
    });
}

// Bind Review Form submit
document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('reviewsGrid')) {
        loadReviews();
        populateReviewClassesDropdown();
        setupStarRatingPicker();

        const form = document.getElementById('reviewForm');
        if (form) {
            form.addEventListener('submit', async (e) => {
                e.preventDefault();

                const nameInput = document.getElementById('reviewStudentName');
                const classInput = document.getElementById('reviewClassName');
                const commentInput = document.getElementById('reviewComment');
                const submitBtn = document.getElementById('reviewSubmitBtn');

                const studentName = nameInput ? nameInput.value.trim() : '';
                const className = classInput ? classInput.value.trim() : '';
                const comment = commentInput ? commentInput.value.trim() : '';
                const rating = selectedStarRating;

                if (!studentName) {
                    showNotification('Please enter your name.', 'error');
                    return;
                }
                if (!className) {
                    showNotification('Please select or specify the class name.', 'error');
                    return;
                }
                if (!comment) {
                    showNotification('Please write your review comment.', 'error');
                    return;
                }
                if (rating < 1 || rating > 5) {
                    showNotification('Please select a star rating between 1 and 5.', 'error');
                    return;
                }

                if (submitBtn) {
                    submitBtn.disabled = true;
                    submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Submitting...';
                }

                try {
                    const response = await fetch(`${API_BASE}/reviews`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            studentName: studentName,
                            className: className,
                            rating: rating,
                            comment: comment
                        })
                    });

                    if (response.ok) {
                        showNotification('Thank you! Your review has been published.', 'success');
                        form.reset();
                        selectedStarRating = 5;
                        updateStarPickerVisuals(5);
                        loadReviews(); // Refresh review cards
                    } else {
                        const errData = await response.json().catch(() => ({}));
                        showNotification(errData.error || 'Failed to submit review.', 'error');
                    }
                } catch (err) {
                    console.error('Submit review error:', err);
                    showNotification('Unable to submit review. Server error.', 'error');
                } finally {
                    if (submitBtn) {
                        submitBtn.disabled = false;
                        submitBtn.innerHTML = '<i class="fa-solid fa-paper-plane me-2"></i>Post Review';
                    }
                }
            });
        }
    }
});
