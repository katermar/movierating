<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="film" type="com.katermar.movierating.entity.Film"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${film.name} | Movierating</title>
</head>
<c:import url="header.jsp"/>
<script src="../../js/film_review.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/film_info.css"/>
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
                            <div class="title2"> TV Series (${film.releaseYear})</div>
                        </div>
                        <!-- end details -->
                    </div>

                    <div class="about-movie">
                        <div class="colum-one">
                            <div class="stars" data-rating="2"></div>

                            <div class="colum-catogary">
                                <c:forEach items="${genre}" var="genre">
                                    <span class="tag">${genre.name}</span>
                                </c:forEach>
                                <div>
                                    <!--HTML5 Custom Data Attributes (data-*)-->
                                </div>
                                <!--end colum-one -->
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
                <h4>Average user rating</h4>
                <h2 class="bold padding-bottom-7">${avgRate}
                    <small>/ 5</small>
                </h2>
                <form action="/controller" method="post">
                    <fieldset class="starRating">
                        <!--HTML5 Custom Data Attributes (data-*)-->
                        <c:choose>
                            <c:when test="${avgRate eq null || avgRate < 1}">
                                <input id="rating1" type="radio" data-length="1" name="rating" value="1"
                                       onchange="this.form.submit()">
                            </c:when>
                            <c:when test="${avgRate >= 1}">
                                <input id="rating1" type="radio" data-length="1" name="rating" value="1"
                                       onchange="this.form.submit()" checked>
                            </c:when>
                        </c:choose>
                        <label for="rating1"></label>

                        <c:choose>
                            <c:when test="${avgRate >= 2 }">
                                <input id="rating2" data-length="2" type="radio" name="rating" value="2"
                                       onchange="this.form.submit()" checked>
                            </c:when>
                            <c:otherwise>
                                <input id="rating2" data-length="2" type="radio" name="rating" value="2"
                                       onchange="this.form.submit()" checked>
                            </c:otherwise>
                        </c:choose>
                        <label for="rating2"></label>

                        <c:choose>
                            <c:when test="${avgRate >= 3}">
                                <input id="rating3" type="radio" data-length="3" name="rating" value="3"
                                       onchange="this.form.submit()" checked>
                            </c:when>
                            <c:otherwise>
                                <input id="rating3" type="radio" data-length="3" name="rating" value="3"
                                       onchange="this.form.submit()">
                            </c:otherwise>
                        </c:choose>
                        <label for="rating3"></label>

                        <c:choose>
                            <c:when test="${avgRate >= 4}">
                                <input id="rating4" data-length="4" type="radio" name="rating" value="4"
                                       onchange="this.form.submit()" checked>
                            </c:when>
                            <c:otherwise>
                                <input id="rating4" data-length="4" type="radio" name="rating" value="4"
                                       onchange="this.form.submit()">
                            </c:otherwise>
                        </c:choose>
                        <label for="rating4"></label>

                        <c:choose>
                            <c:when test="${avgRate eq 5}">
                                <input id="rating5" data-length="5" type="radio" name="rating" value="5"
                                       onchange="this.form.submit()" checked>
                            </c:when>
                            <c:otherwise>
                                <input id="rating5" data-length="5" type="radio" name="rating" value="5"
                                       onchange="this.form.submit()">
                            </c:otherwise>
                        </c:choose>
                        <label for="rating5"></label>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
    <div class="row" style="margin:40px 0 0 5px">
        <div class="col-md-6">
            <div class="well well-sm">
                <div class="text-right">
                    <a class="btn btn-success btn-green" href="#reviews-anchor" id="open-review-box">Leave a Review</a>
                </div>

                <div class="row" id="post-review-box" style="display:none;">
                    <div class="col-md-12">
                        <form accept-charset="UTF-8" action="" method="post">
                            <input id="ratings-hidden" name="rating" type="hidden">
                            <textarea class="form-control animated" cols="50" id="new-review" name="comment"
                                      placeholder="Enter your review here..." rows="5"></textarea>

                            <div class="text-right">
                                <div class="stars starrr" data-rating="0"></div>
                                <a class="btn btn-danger btn-sm" href="#" id="close-review-box"
                                   style="display:none; margin-right: 10px;">
                                    <span class="glyphicon glyphicon-remove"></span>Cancel</a>
                                <button class="btn btn-success btn-lg" type="submit">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div class="row" style="margin-left:5px ">
        <div class="col-sm-7">
            <div class="review-block">
                <%--@elvariable id="review" type="com.katermar.movierating.entity.Review"--%>
                <c:forEach items="${review}" var="review">
                    <div class="row">
                        <div class="col-sm-3 col-md-3 col-xs-3">
                            <img src="http://dummyimage.com/60x60/666/ffffff&text=No+Image" class="img-rounded">
                            <div class="review-block-name">${review.user.login}</div>
                            <div class="review-block-date">${review.date}<br/>1 day ago</div>
                        </div>
                        <div class="col-sm-9">
                            <div class="review-block-rate">
                                <div class="stars starrr" data-rating="0"></div>
                            </div>
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
