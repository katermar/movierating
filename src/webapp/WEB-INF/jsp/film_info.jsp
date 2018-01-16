<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="film" type="com.katermar.movierating.entity.Film"--%>
<html>
<head>
    <title>${film.name} | Movierating</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
</head>
<c:import url="header.jsp"/>
<script src="../../js/film_review.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/film_info.css"/>

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

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-9 col-xs-12">
            <div class="movie-card">

                <div class="container">

                    <a href="#"><img
                            src="${film.poster}"
                            alt="cover" class="cover"/></a>

                    <div class="back-cover">
                        <div class="details">
                            <div class="title1">${film.name}</div>
                            <div class="title2"><fmt:message key="film.info.film"/> (${film.releaseYear})</div>
                            <div class="title2"><fmt:message key="film.info.duration"/> : (${film.duration})</div>
                        </div>
                        <!-- end details -->
                    </div>

                    <div class="about-movie">
                        <div class="colum-one">
                            <div>
                                <a class="label label-danger"
                                   href="/controller?command=films-by-director&id=${film.director.iddirector}">
                                    ${film.director.name}
                                </a>
                            </div>
                            <br/>
                            <div class="colum-catogary">
                                <c:forEach items="${genre}" var="genre">
                                    <a href="/controller?command=films-by-genre&genre=${genre.name}"
                                       style="text-decoration: none;">
                                        <span class="tag">${genre.name}</span>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                        <!--end colum-one -->
                        <div class="colum-second">

                            <p>${film.description}</p>
                        </div>
                    </div>
                    <!--end -about-movie -->
                </div>
                <!--end container -->
            </div>
        </div>
        <div class="col-sm-5 col-md-3 col-xs-6">
            <div class="rating-block">
                <h4><fmt:message key="film.info.avgRate"/></h4>
                <h2 class="bold padding-bottom-7">${avgRate}
                    <small>/ 5</small>
                </h2>
                <c:if test="${sessionScope.user ne null}">
                    <c:import url="stars_form.jsp"/>
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="watch">
                        <input type="hidden" name="id" value="${film.idFilm}">
                        <button type="submit" class="btn btn-info btn-lg">
                            <span class="glyphicon glyphicon-eye-open"></span>
                        </button>
                    </form>
                </c:if>
                <c:if test="${sessionScope.user eq null}">
                    <div class="stars starrr" data-rating="${avgRate}"></div>
                </c:if>
            </div>
        </div>
    </div>
    <c:choose>
        <c:when test="${sessionScope.user ne null}">
            <div class="row" style="margin:40px 0 0 5px">
                <div class="col-md-6">
                    <div class="well well-sm">
                        <div class="text-right">
                            <a class="btn btn-success btn-green" href="#reviews-anchor" id="open-review-box">
                                <fmt:message key="film.info.leaveReview"/>
                            </a>
                        </div>

                        <div class="row" id="post-review-box" style="display:none;">
                            <div class="col-md-12">
                                <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/controller"
                                      method="post">
                                    <input type="hidden" name="command" value="leave-review">
                                    <input type="hidden" name="id" value="${film.idFilm}">
                                    <textarea class="form-control animated" cols="50" id="new-review" name="review"
                                              placeholder="Enter your review here..." rows="5"></textarea>

                                    <div class="text-right">
                                        <a class="btn btn-danger btn-sm" href="#" id="close-review-box"
                                           style="display:none; margin: 10px 10px 10px 10px;">
                                            <span class="glyphicon glyphicon-remove"> </span><fmt:message
                                                key="film.info.cancel"/></a>
                                        <button class="btn btn-success btn-lg" type="submit"><fmt:message
                                                key="film.info.save"/></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">
                <a onclick="document.getElementById('login').style.display='block'"
                   class="btn btn-xs btn-danger pull-right"><fmt:message key="header.login"/></a>
                <strong><fmt:message key="film.info.warningHeader"/></strong><fmt:message
                    key="film.info.warningMessage"/>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="row" style="margin-left:5px ">
        <div class="col-sm-7">
            <div class="review-block">
                <%--@elvariable id="review" type="com.katermar.movierating.entity.Review"--%>
                <c:forEach items="${review}" var="review">
                    <div class="row">
                        <div class="col-sm-3 col-md-3 col-xs-3">
                            <img src="https://image.flaticon.com/icons/svg/145/145850.svg" class="img-rounded"
                                 width="90">
                            <div class="director-name">
                                <c:if test="${sessionScope.user.login eq review.user.login}">
                                    <a class="label label-info"
                                       href="/controller?command=profile-page">${review.user.login}</a><br/>
                                </c:if>
                                <c:if test="${sessionScope.user.login ne review.user.login}">
                                    <span class="label label-info">${review.user.login}</span><br/>
                                </c:if>
                            </div>
                            <div class="review-block-date">${review.date}</div>
                        </div>
                        <div class="col-sm-9">
                            <c:if test="${review.rating.ratingAmount ne 0.}">
                                <div class="review-block-rate">
                                    <div class="stars starrr" data-rating="${review.rating.ratingAmount}"></div>
                                </div>
                            </c:if>
                            <div class="review-block-title">${review.user.realName}</div>
                            <div class="review-block-description">${review.text}</div>
                        </div>
                    </div>
                    <hr/>
                </c:forEach>
            </div>
        </div>
    </div>

</div> <!-- /container -->
</body>
</html>
