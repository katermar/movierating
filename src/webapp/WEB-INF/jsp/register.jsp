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

<div id="register" class="modal-custom">
  <span onclick="document.getElementById('register').style.display='none'"
        class="close-custom" title="Close Modal">&times;</span>
    <!-- Modal Content -->
    <form class="modal-content-custom animate regform" method="post"
          action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="register">
        <span class="logo">
        <h1 class="headername">
            <a href="#">Movie<span>rating</span></a>
        </h1>
        </span>

        <div class="container-custom">
            <label>
                <b><fmt:message key="main.username"/></b>
                <div class="loginError label label-danger pull-right">${loginError}</div>
                <div class="loginSuccess label label-success pull-right">${loginSuccess}</div>
            </label>
            <input type="text" placeholder="enter username" name="uname" required
                   class="uname" pattern="^[a-zA-Z]([a-zA-Z0-9_]+){4,}">

            <label><b><fmt:message key="main.password"/> </b></label>
            <input type="password" placeholder="enter password" id="psw" name="psw"
                   pattern="(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*" required>
            <input type="password" placeholder="repeat password" id="psw-repeat" name="psw"
                   pattern="(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*" required>
            <p id="validate-status"></p>

            <label><b><fmt:message key="profile.email"/></b></label>
            <div class="emails-wrap">
                <div class="email-wrap">
                    <div class="email-input"><input type="email" placeholder="enter e-mail" name="email" required
                                                    pattern="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?">
                    </div>
                </div>
            </div>

            <label><b><fmt:message key="realName"/></b></label>
            <input type="text" placeholder="enter realname" name="realname" required
                   pattern="[a-zA-Z\s\p{IsCyrillic}]">

            <label><b><fmt:message key="main.birthday"/></b></label>
            <input type="date" placeholder="enter date" name="birthday" required>

            <button type="submit"><fmt:message key="header.register"/></button>
        </div>

        <div class="container-custom" style="background-color:#f1f1f1">
            <button type="button"
                    onclick="document.getElementById('register').style.display='none'; document.getElementsByClassName('regform')[0].reset();"
                    class="cancelbtn">
                <fmt:message key="film.info.cancel"/>
            </button>
            <span class="psw"><a href="#"></a></span>
        </div>
    </form>
</div>

