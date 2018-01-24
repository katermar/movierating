<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <c:import url="header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?new"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/films.css?new"/>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'/>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.twbsPagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js?new"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
</head>
<body>

<article>
    <div>
        <div id="wrapper" class="container">
            <div style="display:flex;">
                <c:if test="${genre ne null}">
                    <div class="mx-1">
                        <h3><span class="label label-danger">#${genre}</span></h3>
                    </div>
                </c:if>
                <c:if test="${genres ne null}">
                    <c:forEach items="${genres}" var="genres">
                        <div class="mx-1">
                            <h3><span class="label label-danger">#${genres}</span></h3>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <div style="display:flex;">
                <c:if test="${director ne null}">
                    <div class="mx-1">
                        <h3><span class="label label-warning">#${director}</span></h3>
                    </div>
                </c:if>
                <c:if test="${directors ne null}">
                    <c:forEach items="${directors}" var="directors">
                        <div class="mx-1">
                            <h3><span class="label label-warning">#${directors}</span></h3>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <%--<input type="hidden" name="filmsPerPage" id="filmsPerPage" value="4">--%>
            <div class="row">
                <form method="get" action="/controller?command=films-page" id="formPerPage">
                    <input type="hidden" name="command" id="commandPart" value="${commandPart}">
                    <input type="hidden" name="filmsPerPage" value="4" id="filmsPerPage">

                    <select class="col-md-1 pull-right selectpicker show-tick" id="selectPerPage"
                            data-style="btn-warning">
                        <c:forEach begin="4" end="${filmsCount}" var="item">
                            <option>${item}</option>
                        </c:forEach>
                    </select>
                </form>
            </div>
            <div id="viewport" data-mode="preview">

                <%--@elvariable id="films" type="com.katermar.movierating.entity.Film"--%>
                <input type="hidden" id="filmsCount" value="${filmsCount}">
                <c:forEach items="${films}" var="films">
                    <div class="item container">
                        <div class="row col-custom">
                            <div class="content-box text-center">
                                <a href="/controller?command=film-info&id=${films.idFilm}">
                                    <img src="${films.poster}" alt="About">
                                    <span class="content-link text-uppercase">
                                        <fmt:message key="films.info"/>
                                    </span>
                                </a>
                            </div>
                        </div>
                        <div class="row sb-description">
                            <h3 class="title" title="${films.name}">
                                    ${films.name}
                            </h3>
                            <div class="desc">
                                    ${films.description}
                            </div>
                        </div>
                        <c:if test="${sessionScope.user ne null && sessionScope.user.role eq 'ADMIN'}">
                            <div class="row text-center pull-down">
                                <form action="/controller" method="post" class="edit-form col-md-6 col-sm-6 col-xs-6">
                                    <input type="hidden" name="command" value="edit-film">
                                    <input type="hidden" name="id" value="${films.idFilm}">
                                    <button type="button" class="btn btn-info glyphicon glyphicon-pencil"></button>
                                </form>
                                <form action="/controller" class="delete-form col-md-6 col-sm-6 col-xs-6">
                                    <input type="hidden" name="command" value="delete-film">
                                    <input type="hidden" name="id" value="${films.idFilm}">
                                    <button type="button" id="delete${films.idFilm}"
                                            class="btn btn-danger glyphicon glyphicon-trash"></button>
                                </form>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>

            </div>
            <div class="row">
                <nav aria-label="Page navigation" style="justify-content: center;">
                    <ul id="pagination-films" class="pagination"></ul>
                </nav>
                </nav>
            </div>
        </div>
    </div>
</article>

