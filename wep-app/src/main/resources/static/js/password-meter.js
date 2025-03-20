document.addEventListener('DOMContentLoaded', function() {
    const passwordInput = document.getElementById('password');
    const strengthMeter = document.getElementById('strength-meter-fill');
    const strengthText = document.getElementById('password-strength-text');

    passwordInput.addEventListener('input', function() {
        const password = passwordInput.value;
        let strength = 0;
        let message = '';

        // Length check
        if (password.length >= 8) {
            strength += 25;
        }

        // Uppercase check
        if (/[A-Z]/.test(password)) {
            strength += 25;
        }

        // Lowercase check
        if (/[a-z]/.test(password)) {
            strength += 25;
        }

        // Digits check
        if (/[0-9]/.test(password)) {
            strength += 12.5;
        }

        // Special character check
        if (/[^A-Za-z0-9]/.test(password)) {
            strength += 12.5;
        }

        // Update the meter fill width and color
        strengthMeter.style.width = strength + '%';

        // Update colors based on strength
        if (strength < 25) {
            strengthMeter.style.backgroundColor = '#ff4d4d'; // red
            message = 'Too weak';
        } else if (strength < 50) {
            strengthMeter.style.backgroundColor = '#ffa64d'; // orange
            message = 'Weak';
        } else if (strength < 75) {
            strengthMeter.style.backgroundColor = '#ffff4d'; // yellow
            message = 'Medium';
        } else if (strength < 100) {
            strengthMeter.style.backgroundColor = '#4dff4d'; // light green
            message = 'Strong';
        } else {
            strengthMeter.style.backgroundColor = '#2eb82e'; // dark green
            message = 'Very strong';
        }

        // Update message
        strengthText.textContent = 'Password strength: ' + message;

        // If password is empty
        if (password.length === 0) {
            strengthMeter.style.width = '0%';
            strengthText.textContent = 'Password strength: Too short';
        }
    });
});