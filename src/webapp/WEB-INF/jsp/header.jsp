<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'/>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
</head>

<!-- internationalization -->
<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale"/>

<header class="header-basic-light">
    <div class="header-limiter">
        <h1 class="headername"><a>Movie<span>rating</span></a></h1>
        <nav>
            <nav class="menu">
                <span class="menu-item"><a href="/controller?command=main-page"><fmt:message
                        key="header.home"/></a></span>
                <span class="menu-item"><a href="#"><fmt:message key="header.rating"/></a></span>
                <span class="menu-item"><a href="/controller?command=films-page"><fmt:message
                        key="header.films"/></a></span>
                <span class="menu-item"><a href="#"><fmt:message key="header.series"/></a></span>
                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                    <span class="menu-item"><a href="#"><fmt:message key="header.users"/></a></span>
                </c:if>
            </nav>

            <nav class="auth">
                <%-- Change upper right context menu depending on if the user is logged in or not: --%>
                <c:set var="user" scope = "session" value="${sessionScope.user}"/>
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <span class="menu-item" onclick="document.getElementById('login').style.display='block'">
                            <a id="login-ref"><fmt:message key="header.login"/> </a>
                        </span>
                        <span class="menu-item" onclick="document.getElementById('register').style.display='block'">
                            <a id="register-ref"><fmt:message key="header.register"/> </a>
                        </span>
                    </c:when>

                    <c:otherwise>
                        <span class="menu-item">
                            <a id="profile-ref" href="/controller?command=profile-page"><fmt:message
                                    key="header.profile"/></a>
                        </span>
                        <span class="menu-item">
                            <a href="controller?command=logout" id="logout-ref"><fmt:message key="header.logout"/></a>
                        </span>
                    </c:otherwise>
                </c:choose>

                <c:import url="lang.jsp"></c:import>
            </nav>
        </nav>
    </div>
</header>
<c:import url="login.jsp"/>
<c:import url="register.jsp"/>
<script src="../../js/closemodal.js"></script>

