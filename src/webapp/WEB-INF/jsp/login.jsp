<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="login" class="modal-custom">
  <span onclick="document.getElementById('login').style.display='none'"
        class="close-custom" title="Close Modal">&times;</span>

    <!-- Modal Content -->
    <form class="modal-content-custom animate loginform">
        <input type="hidden" name="command" value="login">
        <span class="logo">
        <h1 class="headername">
            <a href="#">Movie<span>rating</span></a>
        </h1>
        </span>
        <div class="imgcontainer">
            <img src="../../img/user.svg" alt="Avatar" class="avatar">
        </div>

        <div class="container-custom">
            <label><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="uname" required>

            <label><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="psw" required>

            <h5 id="error-msg" class="label label-danger">${errorLogin}</h5>

            <button type="button" id="login-submit">Login</button>
        </div>

        <div class="container-custom" style="background-color:#f1f1f1">
            <button type="button"
                    onclick="document.getElementById('login').style.display='none'; document.getElementsByClassName('loginform')[0].reset();"
                    class="cancelbtn">
                Cancel
            </button>
            <span class="psw"><a href="#"></a></span>
        </div>
    </form>
</div>

