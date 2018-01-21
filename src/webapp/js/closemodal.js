var register = document.getElementById('register');
var login = document.getElementById('login');

// When the user clicks anywhere outside of the register, close-custom it
window.onclick = function (event) {
    if (event.target == login) {
        login.style.display = "none";
        document.getElementsByClassName('loginform')[0].reset();
        document.getElementById('validate-status').innerHTML = "";
    } else if (event.target == register) {
        register.style.display = "none";
        document.getElementsByClassName('regform')[0].reset();
        document.getElementById('validate-status').innerHTML = "";
    }
};

