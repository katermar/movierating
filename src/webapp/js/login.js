var login = document.getElementById('login');

// When the user clicks anywhere outside of the register, close-custom it
window.onclick = function (event) {
    if (event.target == login) {
        login.style.display = "none";
    }
}