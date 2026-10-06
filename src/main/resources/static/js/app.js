// ===================================================================
// ARTIST HUB - Common Application JavaScript Helpers & Auth State
// ===================================================================

const API_BASE = '/api';

// Current logged in user helpers
function getCurrentUser() {
    try {
        const u = localStorage.getItem('currentUser');
        return u ? JSON.parse(u) : null;
    } catch (e) {
        return null;
    }
}

function setCurrentUser(user) {
    if (user) {
        localStorage.setItem('currentUser', JSON.stringify(user));
    } else {
        localStorage.removeItem('currentUser');
    }
    updateNavbarAuth();
}

function logout() {
    localStorage.removeItem('currentUser');
    showNotification('Logged out successfully.', 'success');
    setTimeout(() => {
        window.location.href = 'index.html';
    }, 600);
}

// Update navbar according to login state
function updateNavbarAuth() {
    const navList = document.querySelector('.navbar-nav');
    if (!navList) return;

    const user = getCurrentUser();
    let authContainer = document.getElementById('navbarAuthSection');

    if (!authContainer) {
        authContainer = document.createElement('div');
        authContainer.id = 'navbarAuthSection';
        authContainer.className = 'd-flex align-items-center gap-2 ms-lg-2 mt-2 mt-lg-0';
        navList.appendChild(authContainer);
    }

    if (user) {
        // Show Profile, My Courses, and Logout
        authContainer.innerHTML = `
            <a href="profile.html" class="nav-link fw-semibold text-primary d-flex align-items-center gap-1">
                <i class="fa-solid fa-circle-user fa-lg"></i>
                <span>${user.name ? user.name.split(' ')[0] : 'Profile'}</span>
            </a>
            <a href="profile.html#my-courses" class="btn btn-sm btn-outline-secondary rounded-pill px-3">
                <i class="fa-solid fa-book-bookmark me-1"></i> My Courses
            </a>
            <button class="btn btn-sm btn-outline-danger rounded-pill px-3" onclick="logout()" title="Sign Out">
                <i class="fa-solid fa-right-from-bracket me-1"></i> Logout
            </button>
        `;
    } else {
        // Show Login / Register
        authContainer.innerHTML = `
            <a href="login.html" class="btn btn-sm btn-outline-custom px-3">
                <i class="fa-solid fa-user-lock me-1"></i> Login / Register
            </a>
        `;
    }
}

// Format course fee (e.g. "FREE" or "₹499")
function formatFee(fee) {
    if (fee === null || fee === undefined || Number(fee) === 0) {
        return '<span class="price-tag free">FREE</span>';
    }
    return `<span class="price-tag">₹${Number(fee).toLocaleString('en-IN')}</span>`;
}

// Generate category badge HTML
function getCategoryBadge(category) {
    const cat = (category || '').toLowerCase();
    let badgeClass = 'badge-painting';
    if (cat.includes('sketch')) badgeClass = 'badge-sketching';
    else if (cat.includes('pastel')) badgeClass = 'badge-pastel';
    else if (cat.includes('doodle')) badgeClass = 'badge-doodling';

    return `<span class="badge-category ${badgeClass}">${category || 'Art'}</span>`;
}

// Generate mode badge HTML
function getModeBadge(mode) {
    const isOnline = (mode || '').toLowerCase() === 'online';
    const badgeClass = isOnline ? 'badge-online' : 'badge-offline';
    const icon = isOnline ? 'fa-video' : 'fa-location-dot';
    return `<span class="badge-category ${badgeClass}"><i class="fa-solid ${icon} me-1"></i>${mode || 'Class'}</span>`;
}

// Generate seats badge HTML
function getSeatsBadge(availableSeats, maxStudents) {
    if (availableSeats <= 0) {
        return `<span class="seats-badge full"><i class="fa-solid fa-circle-xmark me-1"></i>Full</span>`;
    } else if (availableSeats <= 3) {
        return `<span class="seats-badge few"><i class="fa-solid fa-fire me-1"></i>Only ${availableSeats} left!</span>`;
    }
    return `<span class="seats-badge plenty"><i class="fa-solid fa-check me-1"></i>${availableSeats} seats left</span>`;
}

// Generate star rating icons HTML
function getStarRatingHtml(rating) {
    const stars = Math.round(Number(rating) || 5);
    let html = '';
    for (let i = 1; i <= 5; i++) {
        if (i <= stars) {
            html += '<i class="fa-solid fa-star"></i>';
        } else {
            html += '<i class="fa-regular fa-star"></i>';
        }
    }
    return html;
}

// Show alert / toast notification
function showNotification(message, type = 'success') {
    let container = document.getElementById('notification-toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'notification-toast-container';
        container.className = 'toast-container position-fixed bottom-0 end-0 p-3';
        container.style.zIndex = '9999';
        document.body.appendChild(container);
    }

    const toastId = 'toast-' + Date.now();
    const bgClass = type === 'success' ? 'bg-success text-white' : 'bg-danger text-white';
    const icon = type === 'success' ? 'fa-circle-check' : 'fa-circle-exclamation';

    const toastEl = document.createElement('div');
    toastEl.id = toastId;
    toastEl.className = `toast align-items-center ${bgClass} border-0 shadow-lg`;
    toastEl.setAttribute('role', 'alert');
    toastEl.setAttribute('aria-live', 'assertive');
    toastEl.setAttribute('aria-atomic', 'true');
    toastEl.innerHTML = `
        <div class="d-flex">
            <div class="toast-body d-flex align-items-center gap-2">
                <i class="fa-solid ${icon} fa-lg"></i>
                <span>${message}</span>
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    `;

    container.appendChild(toastEl);
    const toast = new bootstrap.Toast(toastEl, { delay: 4500 });
    toast.show();

    toastEl.addEventListener('hidden.bs.toast', () => {
        toastEl.remove();
    });
}

// Default image helper for class categories if image is not provided
function getClassCategoryImg(category) {
    const cat = (category || '').toLowerCase();
    if (cat.includes('painting')) {
        return 'https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?auto=format&fit=crop&w=600&q=80';
    } else if (cat.includes('sketch')) {
        return 'https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=600&q=80';
    } else if (cat.includes('pastel')) {
        return 'https://images.unsplash.com/photo-1513364776144-60967b0f800f?auto=format&fit=crop&w=600&q=80';
    } else {
        return 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=600&q=80';
    }
}

// Run navbar update when document loads
document.addEventListener('DOMContentLoaded', () => {
    updateNavbarAuth();
});
