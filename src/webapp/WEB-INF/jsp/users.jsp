<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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

<html>
<head>
    <title><fmt:message key="header.users"/> | <fmt:message key="header.name"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css"/>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container">
    <div class="row">
        <div class="well">
            <h1 class="text-center">Vote for your favorite</h1>
            <div class="list-group">
                <%--@elvariable id="user" type="com.katermar.movierating.entity.User"--%>
                <%--@elvariable id="users" type="java.util.Map"--%>
                <c:forEach items="${users}" var="user">
                    <a href="#" class="list-group-item">
                        <div class="media col-md-3">
                            <figure class="pull-left">
                                <img class="center-block media-object img-rounded img-responsive" height="150px"
                                     width="160px" src="${user.key.avatar}"
                                     alt="${user.key.login}">
                            </figure>
                        </div>
                        <div class="col-md-6">
                            <h4 class="list-group-item-heading"> ${user.key.login} </h4>
                            <p class="list-group-item-text">
                                    ${user.key.realName}
                                <i class="glyphicon glyphicon-envelope"></i> ${user.key.email}
                            <hr>
                            <i class="glyphicon glyphicon-gift"></i> ${user.key.dateOfBirth}
                            </p>
                        </div>
                        <div class="col-md-3 text-center">
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="">
                                <c:choose>
                                    <c:when test="${user.key.status eq 'UNBANED'}">
                                        <button type="submit" class="btn btn-danger btn-lg btn-block"><fmt:message
                                                key="users.ban"/></button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn btn-primary btn-lg btn-block"><fmt:message
                                                key="users.unban"/></button>
                                    </c:otherwise>
                                </c:choose>
                            </form>


                            <div class="star-rating">
                                <fmt:formatNumber var="stars" value="${user.value}" maxFractionDigits="0"/>
                                <c:if test="${not empty user.value}">
                                    <c:forEach var="i" begin="1" end="${stars}">
                                        <span class="glyphicon glyphicon-star"></span>
                                    </c:forEach>
                                    <c:forEach var="i" begin="${stars+1}" end="5">
                                        <span class="glyphicon glyphicon-star-empty"></span>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <p><fmt:message key="film.info.avgRate"/> ${user.value}
                                <small> /</small>
                                5
                            </p>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
