var register = document.getElementById('register');
var login = document.getElementById('login');

// When the user clicks anywhere outside of the register, close-custom it
window.onclick = function (event) {
    if (event.target == login) {
        login.style.display = "none";
        document.getElementsByClassName('loginform')[0].reset();
        document.getElementById('validate-status').innerHTML = "";
        $('#error-msg').empty();
    } else if (event.target == register) {
        register.style.display = "none";
        document.getElementsByClassName('regform')[0].reset();
        document.getElementById('validate-status').innerHTML = "";
        $('.loginError').empty();
        $('.loginSuccess').empty();
    }
};

$(document).on('change keyup keydown', '.uname', function () {
    $.ajax({
        type: 'GET',
        url: '/controller?command=check-login&uname=' + $(this).val(),
        success: function (data) {
            var jqObj = jQuery(data);
            $('.loginError').html(jqObj.find('.loginError').html());
            $('.loginSuccess').html(jqObj.find('.loginSuccess').html());
            if ($('.loginError').html().length === 0) {
                $('.pass-send-submit').prop('disabled', true);
            } else {
                $('.pass-send-submit').prop('disabled', false);
            }
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
                $('.nav-custom').html(jqObj.find('.nav-custom').children());
                login.style.display = "none";
            } else {
                $('#error-msg').html(jqObj.find('#error-msg').html());
            }
        }
    })
});

$(document).on('click', '.forgot-button', function () {
    $('.forgot-pass-form')[0].reset();
    $(this).closest('form')[0].reset();
    $(this).closest('form').find('uname').val('');
    $('.forgot-pass-form').find('uname').val('');
    $('.pass-send-submit').prop('disabled', true);
    $(this).find('.loginError').empty();
    $(this).find('.loginSuccess').empty();
});

$(document).on('click', '.pass-send-submit', function () {
    $.ajax({
        type: 'POST',
        data: $(this).closest('form').serialize(),
        url: '/controller',
        error: function (xhr) {
            var jqObj = jQuery($.parseHTML(xhr.responseText));
            $('.alert-custom-forgot').prop('hidden', false);
            $('.alert-custom-forgot').html(jqObj.find('.error-mes-page').html());
            $('.pass-send-submit').prop('disabled', true);
        },
        success: function (data) {
            $('#modalPassword').find('.close-forgot').click();
        }
    })
});

$(document).on('click', '#set-avatar-submit', function () {
    $.ajax({
        type: 'POST',
        data: $(this).closest('form').serialize(),
        url: '/controller',
        error: function (xhr) {
            var jqObj = jQuery($.parseHTML(xhr.responseText));
            $('#avatar-error').html(jqObj.find('.error-mes-page').html());
            $('.avatar-url').val('');
        },
        success: function (data) {
            var jqObj = jQuery(data);
            $('.avatar-img').html(jqObj.find('.avatar-img').children());
            $('.avatar-url').val('');
        }
    })
});



