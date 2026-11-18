
const loginForm = document.getElementById("login-form");
const registerForm = document.getElementById("register-form");

loginForm.addEventListener("submit", function(event) {
    event.preventDefault();

    alert("Login backend coming soon");
});

// Registration form
registerForm.addEventListener("submit", function(event) {
    event.preventDefault();

    const password =
        document.getElementById("register-password").value;

    const confirm =
        document.getElementById("confirm-password").value;

    if (password !== confirm) {
        alert("Passwords do not match");
        return;
    }

    alert("Registration backend coming soon");
});
