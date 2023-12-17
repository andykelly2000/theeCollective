function validateForm() {
    const name = document.getElementById('name').value;
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const age = document.getElementById('age').value;

    // Check if any field is empty
    if (name === '' || username === '' || email === '' || password === '' || confirmPassword === '' || age === '') {
        alert('Please fill in all fields.');
        return false;
    }

    // Check if passwords match
    if (password !== confirmPassword) {
        alert('Passwords do not match.');
        return false;
    }

    return true;
}
