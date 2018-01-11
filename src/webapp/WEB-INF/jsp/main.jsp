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
    <title>Movierating</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'/>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../../js/passwordmatch.js"></script>
</head>
<body>

<c:set var="sessionScope.lastPageURI" value="/controller?command=go-to-main-page"/>
<c:import url="header.jsp"/>
<article>
    <div style="display: block">
        <div id="wrapper">
            <div id="viewport" data-mode="preview">
                <div class="item">
                    <img class="hover-img"
                         src="https://static.wixstatic.com/media/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_e64c6e1141c14913bfaaefbe7500ac0a~mv2.jpg">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="The Killing of a Sacred Deer"
                                                                                                  style="color: rgb(160, 160, 159);">
                        The Killing of a Sacred Deer</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">Steven, a charismatic surgeon, is forced to
                            make an unthinkable sacrifice after his life starts to fall apart, when the behavior of a
                            teenage boy he has taken under his wing turns sinister.
                        </div>
                    </div>
                </div>

                <div class="item">
                    <img src="https://static.wixstatic.com/media/aedce9_7c759d39ce8e465498f9b7b8de5294a8~mv2.jpg/v1/fill/w_276,h_393,al_c,q_80,usm_0.66_1.00_0.01/aedce9_7c759d39ce8e465498f9b7b8de5294a8~mv2.jpg">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="Ya tozhe khochu"
                                                                                                  style="color: rgb(160, 160, 159);">
                        Ya tozhe khochu</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">Sanja the Bandit after a particularly
                            vicious
                            shootout, stumbles into the musician Oleg at the local banya. Following a retelling of a
                            semi-mythological story, the pair decide to make a pilgrimage to a "bell-tower of
                            happiness".
                        </div>
                    </div>
                </div>

                <div class="item">
                    <img src="https://static.wixstatic.com/media/aedce9_f7b63e324e684b5fac8f6c437fe8d1c7~mv2.jpg/v1/fill/w_276,h_404,al_c,q_80,usm_0.66_1.00_0.01/aedce9_f7b63e324e684b5fac8f6c437fe8d1c7~mv2.jpg">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="Interstellar"
                                                                                                  style="color: rgb(160, 160, 159);">
                        Interstellar</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">A team of explorers travel through a
                            wormhole
                            in space in an attempt to ensure humanity's survival.
                        </div>
                    </div>
                </div>

                <div class="item">
                    <img src="https://static.wixstatic.com/media/aedce9_62f4395bbd0041e794e63442ae7afffb~mv2.jpg/v1/fill/w_276,h_411,al_c,q_80,usm_0.66_1.00_0.01/aedce9_62f4395bbd0041e794e63442ae7afffb~mv2.jpg"
                         width="221" height="329">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="Annie Hall"
                                                                                                  style="color: rgb(160, 160, 159);">
                        Annie Hall</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">Neurotic New York comedian Alvy Singer
                            falls in
                            love with the ditzy Annie Hall.
                        </div>
                    </div>
                </div>

                <div class="item">
                    <img src="https://static.wixstatic.com/media/aedce9_467cabc6ff2b419eb23675a12449b2a6~mv2.jpg/v1/fill/w_276,h_409,al_c,q_80,usm_0.66_1.00_0.01/aedce9_467cabc6ff2b419eb23675a12449b2a6~mv2.jpg">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="Inglourious Basterds"
                                                                                                  style="color: rgb(160, 160, 159);">
                        Inglourious Basterds</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">In Nazi-occupied France during World War
                            II, a
                            plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre
                            owner's vengeful plans for the same.
                        </div>
                    </div>
                </div>

                <div class="item">
                    <img src="https://static.wixstatic.com/media/aedce9_f70f7924395049e7811fbe71696b18c9~mv2.jpg/v1/fill/w_276,h_390,al_c,q_80,usm_0.66_1.00_0.01/aedce9_f70f7924395049e7811fbe71696b18c9~mv2.jpg">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="Snatch"
                                                                                                  style="color: rgb(160, 160, 159);">
                        Snatch</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">Unscrupulous boxing promoters, violent
                            bookmakers, a Russian gangster, incompetent amateur robbers, and supposedly Jewish jewelers
                            fight to track down a priceless stolen diamond.
                        </div>
                    </div>
                </div>

                <div class="item">
                    <img src="https://static.wixstatic.com/media/aedce9_71b2b5d988f24a8ba955eec054be4cac~mv2.jpg/v1/fill/w_276,h_400,al_c,q_80,usm_0.66_1.00_0.01/aedce9_71b2b5d988f24a8ba955eec054be4cac~mv2.jpg">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="Brat 2"
                                                                                                  style="color: rgb(160, 160, 159);">
                        Brat 2</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">Here goes "kirdyk" to America, as it was
                            promised in first part.
                        </div>
                    </div>
                </div>

                <div class="item">
                    <img src="https://static.wixstatic.com/media/aedce9_a8c417baade14f1484e8801b62baee41~mv2.jpg/v1/fill/w_276,h_389,al_c,q_80,usm_0.66_1.00_0.01/aedce9_a8c417baade14f1484e8801b62baee41~mv2.jpg">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="Lock, Stock and Two Smoking Barrels"
                                                                                                  style="color: rgb(160, 160, 159);">
                        Lock, Stock and Two Smoking Barrels</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">A botched card game in London triggers four
                            friends, thugs, weed-growers, hard gangsters, loan sharks and debt collectors to collide
                            with
                            each other in a series of unexpected events, all for the sake of weed, cash and two antique
                            shotguns.
                        </div>
                    </div>
                </div>

                <div class="item">
                    <img src="https://static.wixstatic.com/media/aedce9_d18b9b5b6d2345fe806cacb335754b9f~mv2.jpg/v1/fill/w_276,h_389,al_c,q_80,usm_0.66_1.00_0.01/aedce9_d18b9b5b6d2345fe806cacb335754b9f~mv2.jpg"
                         width="221" height="311">
                    <div class="sb-description" style="background-color: rgb(255, 255, 255);"><h3 class="title"
                                                                                                  title="Goodfellas"
                                                                                                  style="color: rgb(160, 160, 159);">
                        Goodfellas</h3>
                        <div class="desc" style="color: rgb(160, 160, 159);">The story of Henry Hill and his life in the
                            mob, covering his relationship with his wife Karen Hill and his Mob partners Jimmy Conway
                            and
                            Tommy DeVito in the Italian-American crime syndicate.
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</article>
<c:import url="login.jsp"/>
<c:import url="register.jsp"/>
<script src="../../js/closemodal.js"></script>
</body>
</html>
