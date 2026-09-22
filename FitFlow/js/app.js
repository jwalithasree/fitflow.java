/**
 * FitFlow Academic AOP Project - Frontend Presentation Bridge
 *
 * IMPORTANT ACADEMIC / VIVA NOTE:
 * In strict compliance with the project architecture specifications:
 * - NO calculations (BMI, Calories, Water, Macros, Scoring) are performed in JavaScript.
 * - ALL business logic, recommendation algorithms, state tracking, and mathematical
 *   computations reside 100% inside Java backend classes.
 * - This script acts exclusively as an asynchronous HTTP presentation bridge, fetching
 *   calculated results via REST APIs and updating DOM elements.
 */

const API_BASE = '/api';

// Utility Toast Notification System
function showToast(message, type = 'info') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        document.body.appendChild(container);
    }
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    const icon = type === 'success' ? '✓' : (type === 'error' ? '✕' : 'ℹ');
    toast.innerHTML = `<span>${icon}</span> <span>${message}</span>`;
    container.appendChild(toast);
    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transition = 'opacity 0.4s ease';
        setTimeout(() => toast.remove(), 400);
    }, 3500);
}

// Global API Helper
async function apiCall(endpoint, method = 'GET', body = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    if (body) {
        options.body = JSON.stringify(body);
    }

    try {
        const response = await fetch(`${API_BASE}${endpoint}`, options);
        const result = await response.json();
        if (!response.ok || result.success === false) {
            throw new Error(result.error || `HTTP ${response.status}`);
        }
        return result.data;
    } catch (err) {
        console.error(`API Error on ${endpoint}:`, err);
        showToast(err.message, 'error');
        throw err;
    }
}

// Set active navigation link based on current page
document.addEventListener('DOMContentLoaded', () => {
    const currentPath = window.location.pathname;
    const links = document.querySelectorAll('.nav-links a');
    links.forEach(link => {
        if (currentPath.endsWith(link.getAttribute('href'))) {
            link.classList.add('active');
        }
    });

    // Populate user initials & name in navbar if badge exists
    loadGlobalNavbarUser();
});

async function loadGlobalNavbarUser() {
    try {
        const data = await apiCall('/dashboard');
        const userBadge = document.querySelector('.user-info-text');
        const userAvatar = document.querySelector('.user-avatar');
        if (userBadge && data.userName) {
            userBadge.textContent = data.userName;
        }
        if (userAvatar && data.userName) {
            const initials = data.userName.split(' ').map(n => n[0]).join('').substring(0, 2).toUpperCase();
            userAvatar.textContent = initials;
        }
    } catch (e) {
        // Silently ignore if on setup/welcome page
    }
}
