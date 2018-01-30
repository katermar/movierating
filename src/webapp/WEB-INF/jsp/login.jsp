<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale"/>
<html lang="${sessionScope.locale}">

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
            <label>
                <b><fmt:message key="main.username"/></b>
                <span class="label label-warning labelError">${loginError}</span>
            </label>
            <input type="text" placeholder="Enter Username" name="uname" class="uname" required>

            <label><b><fmt:message key="main.password"/> </b></label>
            <input type="password" placeholder="Enter Password" name="psw" required>

            <h5 id="error-msg" class="label label-danger">${errorLogin}</h5>

            <button type="button" id="login-submit"><fmt:message key="header.login"/></button>
        </div>

        <div class="container-custom" style="background-color:#f1f1f1">
            <button type="button"
                    onclick="document.getElementById('login').style.display='none'; document.getElementsByClassName('loginform')[0].reset();"
                    class="cancelbtn">
                <fmt:message key="film.info.cancel"/>
            </button>
            <button type="button" data-toggle="modal" data-target="#modalPassword" class="forgot-button">
                <fmt:message key="main.forgot"/>
            </button>
        </div>
    </form>
</div>

<div class="modal fade" id="modalPassword" tabindex="-1" role="dialog" aria-labelledby="modalPasswordTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="/controller" method="post" class="forgot-pass-form">
            <input type="hidden" name="command" value="forgot-password">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="container-fluid">
                        <div class="row">
                            <button type="button col-md-1 pull-right" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title col-md-11 text-center" id="modalPasswordTitle">
                                <fmt:message key="profile.changePassword"/>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="form-group">
                            <label>
                                <b><fmt:message key="main.username"/></b>
                            </label>
                            <input type="text" placeholder="enter username" name="uname" required
                                   class="uname form-control" pattern="^[a-zA-Z]([a-zA-Z0-9_]+){4,}">
                            <h4><fmt:message key="profile.changePassword.message"/></h4>
                            <div class="alert alert-danger alert-custom-forgot" hidden></div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary close-forgot" data-dismiss="modal"><fmt:message
                            key="add.common.close"/></button>
                    <button type="button" disabled class="btn btn-success pass-send-submit"><fmt:message
                            key="profile.changePassword"/></button>
                </div>
            </div>
        </form>
    </div>
</div>

