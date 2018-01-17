
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
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
      integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
<html>
<head>
    <title><fmt:message key="error.title"/> | <fmt:message key="header.name"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container" style="padding-top: 30px">
    <div class="jumbotron">
        <div class="text-center"><i class="fa fa-5x fa-frown-o" style="color:#d9534f;"></i></div>
        <h1 class="text-center">${pageContext.errorData.statusCode} ${pageContext.exception.message}<p></p>
            <p>
                <small class="text-center"><fmt:message key="error.broke"/></small>
            </p>
        </h1>
        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
            <b>Error: </b>${pageContext.exception} <br/>
            <b>Stack trace:</b><br/>
            <c:forEach var="trace"
                       items="${pageContext.exception.stackTrace}">
                <p>${trace}</p>
            </c:forEach>
        </c:if>
        <p class="text-center"><fmt:message key="error.message.back"/></p>
        <p class="text-center"><a class="btn btn-primary" href="\controller?command=main-page"><i
                class="fa fa-home"></i><fmt:message key="error.message.home"/></a></p>
    </div>
</div>
</body>
</html>
