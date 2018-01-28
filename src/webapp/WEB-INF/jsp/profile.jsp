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
            <c:set var="passAllowance" value=""/>
            <c:if test="${sessionScope.user.status eq 'UNCONFIRMED'}">
                <c:set var="passAllowance" value="disabled"/>
                <div class="col-md-12 col-xs-12">
                    <div class="alert alert-warning">
                        <button data-toggle="modal" data-target="#modalEmail" class="btn btn-xs btn-danger pull-right">
                            <fmt:message key="profile.sendEmail"/>
                        </button>
                        <strong><fmt:message key="film.info.warningHeader"/></strong>
                        <fmt:message key="profile.warningEmail"/>
                    </div>
                </div>
            </c:if>
            <div class="col-xs-12 col-md-6">
                <div class="well">
                    <div class="row">
                        <div class="col-sm-6 col-md-4">
                            <img src="${sessionScope.user.avatar}" alt="" class="img-rounded img-responsive"/>
                        </div>
                        <div class="col-sm-6 col-md-8">
                            <h2>${sessionScope.user.login}</h2>
                            <h4>${sessionScope.user.realName}</h4>
                            <p>
                                <c:if test="${sessionScope.user.email ne null}">
                                <i class="glyphicon glyphicon-envelope"></i>${sessionScope.user.email}
                                </c:if>
                                <br/>
                                <c:if test="${sessionScope.user.dateOfBirth ne null}">
                                <i class="glyphicon glyphicon-gift"></i>${sessionScope.user.dateOfBirth}
                                </c:if>
                            <hr/>
                            <button data-toggle="modal" data-target="#modalEdit"
                                    class="btn btn-md btn-info pull-right">
                                <fmt:message key="profile.editProfile"/>
                            </button>
                            <button data-toggle="modal" ${passAllowance} data-target="#modalPassword"
                                    class="btn btn-md btn-warning pull-left">
                                <fmt:message key="profile.changePassword"/>
                            </button>
                            </p>
                        </div>


                    </div>
                </div>
            </div>
            <div class="col-md-6 col-xs-12">
                <form action="/controller" method="post">
                    <div class="col-md-12 col-xs-12 well">
                        <input type="hidden" name="command" value="set-avatar">
                        <div class="input-group col-md-7 col-xs-7" style="float: inherit">
                            <div class="input-group-addon"><i
                                    class="glyphicon glyphicon-link center-block"></i></div>
                            <input class="form-control" type="url" name="file" placeholder="max. 300x300"
                                   pattern="^(https|http).+(jpg|svg|gif|png)$" required>
                        </div>
                        <button class="col-md-4 col-xs-4 btn btn-success pull-right" type="submit">
                            <i class="glyphicon glyphicon-user"></i>
                            <fmt:message key="profile.setAvatar"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-md-6">
                <div class="well">
                    <div class="row">
                        <div class="col-xs-12 col-md-6 text-center">
                            <fmt:formatNumber var="avg" value="${userAverageRating}" maxFractionDigits="2"/>
                            <h1 class="rating-num">${avg}</h1>
                            <div class="star-rating">
                                <fmt:formatNumber var="stars" value="${userAverageRating}" maxFractionDigits="0"/>
                                <c:forEach var="i" begin="1" end="${stars}">
                                    <span class="glyphicon glyphicon-star"></span>
                                </c:forEach>
                                <c:forEach var="i" begin="${stars+1}" end="5">
                                    <span class="glyphicon glyphicon-star-empty"></span>
                                </c:forEach>
                            </div>
                            <div>
                                <span class="glyphicon glyphicon-user"></span>${total} total
                            </div>
                            <div>
                                <span class="glyphicon glyphicon-signal"></span>${sessionScope.user.levelPoints} points
                                <b> ${sessionScope.user.level} </b>
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
<div class="modal fade" id="modalEmail" tabindex="-1" role="dialog" aria-labelledby="modalEmailTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="send-email">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="container-fluid">
                        <div class="row">
                            <button type="button col-md-1 pull-right" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title col-md-11 text-center" id="modalEmailTitle">
                                <fmt:message key="profile.sendEmail"/>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="form-group">
                            <label for="email"><fmt:message key="profile.changeEmail"/></label>
                            <input class="form-control" type="email" id="email" placeholder="enter e-mail" name="email"
                                   required value="${sessionScope.user.email}"
                                   pattern="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                            key="add.common.close"/></button>
                    <button type="submit" class="btn btn-primary"><fmt:message key="profile.sendEmail"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="modalEditTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="edit-profile">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="container-fluid">
                        <div class="row">
                            <button type="button col-md-1 pull-right" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title col-md-11 text-center" id="modalEditTitle">
                                <fmt:message key="header.profile"/>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="form-group">
                            <label for="email-edit"><fmt:message key="profile.email"/></label>
                            <input class="form-control" type="email" id="email-edit" placeholder="enter e-mail"
                                   name="email"
                                   value="${sessionScope.user.email}"
                                   pattern="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?">
                        </div>
                        <div class="form-group">
                            <label><b>real name</b></label>
                            <input class="form-control" type="text" placeholder="enter realname" name="realname"
                                   pattern="[a-zA-Z\s\p{IsCyrillic}]" value="${sessionScope.user.realName}">
                        </div>
                        <div class="form-group">
                            <label><b>date of birth</b></label>
                            <input class="form-control" type="date" placeholder="enter date" name="birthday"
                                   value="${sessionScope.user.dateOfBirth}">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                            key="add.common.close"/></button>
                    <button type="submit" class="btn btn-success"><fmt:message key="profile.save"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal fade" id="modalPassword" tabindex="-1" role="dialog" aria-labelledby="modalPasswordTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="send-new-password">
            <input type="hidden" name="email" value="${sessionScope.user.email}">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="container-fluid">
                        <div class="row">
                            <button type="button col-md-1 pull-right" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title col-md-11 text-center" id="modalPasswordTitle">
                                <fmt:message key="profile.changePassword"/>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="form-group">
                            <h4><fmt:message key="profile.changePassword.message"/></h4>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                            key="add.common.close"/></button>
                    <button type="submit" class="btn btn-success"><fmt:message key="profile.changePassword"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>