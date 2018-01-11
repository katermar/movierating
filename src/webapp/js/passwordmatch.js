$(document).ready(function () {
    $("#psw-repeat").keyup(validate);
});


function validate() {
    var password1 = $("#psw").val();
    var password2 = $("#psw-repeat").val();


    if (password1 == password2 && password1.toString().trim() != "" && password2.toString().trim() != "") {
        $("#validate-status").text("passwords match!");
    }
    else {
        $("#validate-status").text("passwords don't match!");
    }

}