// Show register form
function showRegister() {
  document.getElementById('loginFormContainer').classList.remove('active');
  document.getElementById('registerFormContainer').classList.add('active');
  document.getElementById('page-title').innerText = 'Register';

  // Clear fields in log in form
  document.getElementById('loginForm').reset();
}

// Show log-in form
function showLogin() {
  document.getElementById('registerFormContainer').classList.remove('active');
  document.getElementById('loginFormContainer').classList.add('active');
  document.getElementById('page-title').innerText = 'Login';

  // Clear fields in register form
  document.getElementById('registerForm').reset();
}

// Log-in form
document.getElementById('loginForm').addEventListener('submit', async (event) => {
  event.preventDefault();

  const username = document.getElementById('login-username').value;
  const password = document.getElementById('login-password').value;

  // Call backend API to authenticate user
  const response = await fetch('/login/authenticate', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`,
  });

  const result = await response.json(); // Parse the JSON response
  if (response.ok) {
    // Use the message field from the API response
    alert(result.message);
    window.location.href = '/home'; // Redirect on successful login
  } else {
    // Show the message from the API response on failure
    alert(result.message || 'Login failed. Please try again.');
    // Clear fields
    document.getElementById('loginForm').reset();
  }
});

// Registration form
document.getElementById('registerForm').addEventListener('submit', async (event) => {
  event.preventDefault();

  const username = document.getElementById('register-username').value;
  const password = document.getElementById('register-password').value;

  // Call backend API to register user
  const response = await fetch('/login/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`,
  });

  const result = await response.json(); // Parse the JSON response
  if (response.ok) {
    alert(result.message); // Display the success message
    showLogin(); // Redirect to login after successful registration
  } else {
    // Display registration error message or default error in case of internal server error
    alert(result.message || 'Registration failed. Please try again.');
    // Clear fields
    document.getElementById('registerForm').reset();
  }
});
