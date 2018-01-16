<%--@elvariable id="currentUser" type="com.katermar.movierating.entity.User"--%>
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
<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/css/profile.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4"
        crossorigin="anonymous"></script>

<html>
<head>
    <title><fmt:message key="header.profile"/> | Movierating</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
</head>
<body>
<c:import url="header.jsp"/>
<div class="centered">
    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-md-6">
                <div class="well well-sm">
                    <div class="row">
                        <div class="col-sm-6 col-md-4">
                            <img src="http://placehold.it/380x500" alt="" class="img-rounded img-responsive"/>
                        </div>
                        <div class="col-sm-6 col-md-8">
                            <h4>${sessionScope.user.realName}</h4>
                            <p>
                                <i class="glyphicon glyphicon-envelope"></i>${sessionScope.user.email}
                                <br/>
                                <i class="glyphicon glyphicon-gift"></i>${sessionScope.user.dateOfBirth}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-md-6">
                <div class="well well-sm">
                    <div class="row">
                        <div class="col-xs-12 col-md-6 text-center">
                            <h1 class="rating-num">
                                4</h1>
                            <div class="rating">
                                <span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star">
                            </span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star">
                            </span><span class="glyphicon glyphicon-star-empty"></span>
                            </div>
                            <div>
                                <span class="glyphicon glyphicon-user"></span>${total} total
                            </div>
                        </div>
                        <c:set var="ratings" value="${ratings}"/>
                        <div class="col-xs-12 col-md-6">
                            <div class="row rating-desc">
                                <div class="col-xs-3 col-md-3 text-right">
                                    <span class="glyphicon glyphicon-star"></span>5
                                </div>
                                <div class="col-xs-8 col-md-9">
                                    <div class="progress progress-striped">
                                        <div class="progress-bar progress-bar-success progress-bar-striped active"
                                             role="progressbar"
                                             aria-valuenow="20"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: ${ratings['5'] / total * 100}%">
                                            <span class="sr-only">${ratings['5']}</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- end 5 -->
                                <div class="col-xs-3 col-md-3 text-right">
                                    <span class="glyphicon glyphicon-star"></span>4
                                </div>
                                <div class="col-xs-8 col-md-9">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-success progress-bar-striped active"
                                             role="progressbar"
                                             aria-valuenow="20"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: ${ratings['4'] / total * 100}%">
                                            <span class="sr-only">${ratings['4']}</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- end 4 -->
                                <div class="col-xs-3 col-md-3 text-right">
                                    <span class="glyphicon glyphicon-star"></span>3
                                </div>
                                <div class="col-xs-8 col-md-9">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-info progress-bar-striped active"
                                             role="progressbar"
                                             aria-valuenow="20"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: ${ratings['3'] / total * 100}%">
                                            <span class="sr-only">${ratings['3']}</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- end 3 -->
                                <div class="col-xs-3 col-md-3 text-right">
                                    <span class="glyphicon glyphicon-star"></span>2
                                </div>
                                <div class="col-xs-8 col-md-9">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-warning progress-bar-striped active"
                                             role="progressbar"
                                             aria-valuenow="20"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: ${ratings['2'] / total * 100}%">
                                            <span class="sr-only">${ratings['2']}</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- end 2 -->
                                <div class="col-xs-3 col-md-3 text-right">
                                    <span class="glyphicon glyphicon-star"></span>1
                                </div>
                                <div class="col-xs-8 col-md-9">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-danger progress-bar-striped active"
                                             role="progressbar"
                                             aria-valuenow="80"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: ${ratings['1'] / total * 100}%">
                                            <span class="sr-only">${ratings['1']}</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- end 1 -->
                            </div>
                            <!-- end row -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>