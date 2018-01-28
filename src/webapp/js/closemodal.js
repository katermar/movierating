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

$(document).on('change keyup keydown', '#uname', function () {
    $.ajax({
        type: 'GET',
        url: '/controller?command=check-login&uname=' + $(this).val(),
        success: function (data) {
            var jqObj = jQuery(data);
            $('#loginError').html(jqObj.find('#loginError').html());
            $('#loginSuccess').html(jqObj.find('#loginSuccess').html());
        }
    })
});

$(document).on('click', '#login-submit', function () {
    $.ajax({
        type: 'GET',
        data: $(this).parent().parent().serialize(),
        url: '/controller',
        success: function (data) {
            var jqObj = jQuery(data);
            var s = jqObj.find('#profile-ref');
            if (jqObj.find('#profile-ref').length != 0) {
                $('.auth').html(jqObj.find('.auth').children());
                login.style.display = "none";
            } else {
                $('#error-msg').html(jqObj.find('#error-msg').html());
            }
        }
    })
});



