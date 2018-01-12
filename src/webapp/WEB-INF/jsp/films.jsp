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
            <div id="viewport" data-mode="preview">
                <%--@elvariable id="films" type="com.katermar.movierating.entity.Film"--%>
                <c:forEach items="${films}" var="films">
                    <div class="item">
                        <div class="col-custom">
                            <div class="content-box text-center">
                                <a href="#">
                                    <img src="${films.poster}"
                                         alt="About">
                                    <span class="content-link text-uppercase">
                                    <fmt:message key="films.info"/>
                </span>
                                </a>
                            </div>
                        </div>
                        <div class="sb-description"><h3 class="title"
                                                        title="${films.name}">
                                ${films.name}</h3>
                            <div class="desc">${films.description}
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="The Killing of a Sacred Deer">--%>
                <%--The Killing of a Sacred Deer</h3>--%>
                <%--<div class="desc">Steven, a charismatic surgeon, is forced to--%>
                <%--make an unthinkable sacrifice after his life starts to fall apart, when the behavior of a--%>
                <%--teenage boy he has taken under his wing turns sinister.--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="Ya tozhe khochu">--%>
                <%--Ya tozhe khochu</h3>--%>
                <%--<div class="desc">Sanja the Bandit after a particularly vicious shootout, stumbles into the musician Oleg at the local banya. Following a retelling of a semi-mythological story, the pair decide to make a pilgrimage to a "bell-tower of happiness".--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="Interstellar">--%>
                <%--Interstellar</h3>--%>
                <%--<div class="desc">A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="Annie Hall">--%>
                <%--Annie Hall</h3>--%>
                <%--<div class="desc">Neurotic New York comedian Alvy Singer falls in love with the ditzy Annie Hall.--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="Inglourious Basterds">--%>
                <%--Inglourious Basterds</h3>--%>
                <%--<div class="desc">In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner's vengeful plans for the same.--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="Snatch">--%>
                <%--Snatch</h3>--%>
                <%--<div class="desc">Unscrupulous boxing promoters, violent bookmakers, a Russian gangster, incompetent amateur robbers, and supposedly Jewish jewelers fight to track down a priceless stolen diamond.--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="Brat 2">--%>
                <%--Brat 2</h3>--%>
                <%--<div class="desc">Here goes "kirdyk" to America, as it was promised in first part.--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="Lock, Stock and Two Smoking Barrels">--%>
                <%--Lock, Stock and Two Smoking Barrels</h3>--%>
                <%--<div class="desc">A botched card game in London triggers four friends, thugs, weed-growers, hard gangsters, loan sharks and debt collectors to collide with each other in a series of unexpected events, all for the sake of weed, cash and two antique shotguns.--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="item">--%>
                <%--<div class="col-custom">--%>
                <%--<div class="content-box text-center">--%>
                <%--<a href="#">--%>
                <%--<img src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg"--%>
                <%--alt="About 1">--%>
                <%--<span class="content-link text-uppercase">--%>
                <%--Support Team--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="sb-description"><h3 class="title"--%>
                <%--title="Goodfellas">--%>
                <%--Goodfellas</h3>--%>
                <%--<div class="desc">The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his Mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>
</article>

