<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>

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
    <title><fmt:message key="header.rating"/> | <fmt:message key="header.name"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
</head>
<body>
<!DOCTYPE html>
<html lang='en' class=''>
<head>
    <script src='//production-assets.codepen.io/assets/editor/live/console_runner-079c09a0e3b9ff743e39ee2d5637b9216b3545af0de366d4b9aad9dc87e26bfd.js'></script>
    <script src='//production-assets.codepen.io/assets/editor/live/events_runner-73716630c22bbc8cff4bd0f07b135f00a0bdc5d14629260c3ec49e5606f98fdd.js'></script>
    <script src='//production-assets.codepen.io/assets/editor/live/css_live_reload_init-2c0dc5167d60a5af3ee189d570b1835129687ea2a61bee3513dee3a50c115a77.js'></script>
    <meta charset='UTF-8'>
    <meta name="robots" content="noindex">
    <link rel="shortcut icon" type="image/x-icon"
          href="//production-assets.codepen.io/assets/favicon/favicon-8ea04875e70c4b0bb41da869e81236e54394d63638a1ef12fa558a4a835f1164.ico"/>
    <link rel="mask-icon" type=""
          href="//production-assets.codepen.io/assets/favicon/logo-pin-f2d2b6d2c61838f7e76325261b7195c27224080bc099486ddd6dccb469b8e8e6.svg"
          color="#111"/>
    <link rel="canonical"
          href="https://codepen.io/ryanparag/pen/oWrLPr?depth=everything&order=popularity&page=5&q=card+list&show_forks=false"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css'>
    <link rel='stylesheet prefetch' href='https://fonts.googleapis.com/icon?family=Material+Icons'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/rating.css"/>
</head>

<mytags:header/>

<body>
<div id="movie-card-rating-list">
    <c:forEach items="${filmsMap}" var="item">
    <!-- Card 1 -->
    <div class="movie-card-rating" style="background-image: url('${item.key.poster}');">
        <div class="color-overlay">
            <div class="movie-share">
                <a class="movie-share__icon" href="#">
                    <i class="material-icons">&#xe87d</i>
                </a>
                <a class="movie-share__icon" href="#">
                    <i class="material-icons">&#xe253</i>
                </a>
                <a class="movie-share__icon" href="#">
                    <i class="material-icons">&#xe80d</i>
                </a>
            </div>
            <div class="movie-content">
                <div class="movie-header">
                    <a href="/controller?command=film-info&id=${item.key.idFilm}"><h1
                            class="movie-title">${item.key.name}</h1></a>
                    <h4 class="movie-info">(${item.key.releaseYear})</h4>
                </div>
                <p class="movie-desc">${item.key.description}</p>
                <a class="btn btn-info" href="#"><fmt:message key="header.rating"/>
                    <fmt:formatNumber value="${item.value}" maxFractionDigits="2"/>
                </a>
            </div>
        </div>
    </div>
    </c:forEach>
</body>
</html>
</body>
</html>
