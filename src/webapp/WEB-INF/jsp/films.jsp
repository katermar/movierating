<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Movierating | Films</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/films.css"/>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'/>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
</head>
<body>

<c:import url="header.jsp"/>
<article>
    <div>
        <div id="wrapper">
            <c:if test="${genre ne null}">
                <div class="mx-1">
                    <h3><span class="label label-danger">#${genre}</span></h3>
                </div>
            </c:if>
            <c:if test="${director ne null}">
                <div class="mx-1">
                    <h3><span class="label label-warning">#${director}</span></h3>
                </div>
            </c:if>
            <div id="viewport" data-mode="preview">
                <%--@elvariable id="films" type="com.katermar.movierating.entity.Film"--%>
                <c:forEach items="${films}" var="films">
                    <div class="item">
                        <div class="col-custom">
                            <div class="content-box text-center">
                                <a href="/controller?command=film-info&id=${films.idFilm}">
                                    <img src="${films.poster}" alt="About">
                                    <span class="content-link text-uppercase">
                                        <fmt:message key="films.info"/>
                                    </span>
                                </a>
                            </div>
                        </div>
                        <div class="sb-description">
                            <h3 class="title" title="${films.name}">
                                    ${films.name}
                            </h3>
                            <div class="desc">
                                    ${films.description}
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</article>

