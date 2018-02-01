<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<html lang="${sessionScope.locale}">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Movierating | Films</title>
    <mytags:header/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/films.css"/>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
    <link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'/>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.twbsPagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>
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
                                <div class="delete-form col-md-6 col-sm-6 col-xs-6">
                                    <button type="button"
                                            class="btn btn-info glyphicon glyphicon-pencil"
                                            data-toggle="modal" data-target="#modalFilm${films.idFilm}"></button>
                                </div>
                                <form action="/controller" method="post" class="edit-form col-md-6 col-sm-6 col-xs-6">
                                    <input type="hidden" name="command" value="delete-film">
                                    <input type="hidden" name="id" value="${films.idFilm}">
                                    <button type="button" class="btn btn-danger glyphicon glyphicon-trash"></button>
                                </form>
                            </div>

                            <%--modal--%>
                            <div class="modal fade" id="modalFilm${films.idFilm}" tabindex="-1" role="dialog"
                                 aria-labelledby="exampleModalLongTitle"
                                 aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <form action="/controller" method="post">
                                        <input type="hidden" name="command" value="add-film">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <button type="button col-md-1 pull-right" class="close"
                                                                data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                        <h3 class="modal-title col-md-11 text-center modalFilmTitle">
                                                            <fmt:message
                                                                    key="header.edit"/> <fmt:message
                                                                key="add.film"/></h3>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-body">
                                                <div class="container-fluid">
                                                    <div class="form-group">
                                                        <label for="filmName${films.idFilm}"><fmt:message
                                                                key="add.film.name"/></label>
                                                        <input class="form-control" type="text" placeholder="name"
                                                               id="filmName${films.idFilm}" name="name"
                                                               value="${films.name}"
                                                               required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="year${films.idFilm}"><fmt:message
                                                                key="add.film.releaseYear"/></label>
                                                        <input class="form-control" type="number"
                                                               placeholder="release year" id="year${films.idFilm}"
                                                               name="year"
                                                               max="2018" min="1900" value="${films.releaseYear}"
                                                               required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="duration${films.idFilm}"><fmt:message
                                                                key="add.film.duration"/></label>
                                                        <input class="form-control" type="number" placeholder="duration"
                                                               id="duration${films.idFilm}"
                                                               name="duration"
                                                               min="1" value="${films.duration}"
                                                               required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="poster${films.idFilm}"><fmt:message
                                                                key="add.film.poster"/></label>
                                                        <input class="form-control" type="url" name="poster"
                                                               id="poster${films.idFilm}"
                                                               placeholder="poster" value="${films.poster}"
                                                               pattern="^(https|http).+(jpg|svg|gif|png)$">
                                                    </div>
                                                        <%--@elvariable id="genres" type="java.util.List"--%>
                                                        <%--@elvariable id="genreModal" type="com.katermar.movierating.entity.Genre"--%>
                                                    <div class="form-group">
                                                        <label><fmt:message key="add.genre"/></label>
                                                        <div class="btn-group btn-group-toggle col-md-12 col-sm-12 col-xs-12 text-center"
                                                             data-toggle="buttons">
                                                            <c:if test="${genresModal ne null}">
                                                                <c:forEach items="${genresModal}" var="genreModal">
                                                                    <span class="button-checkbox"
                                                                          style="margin: 10px 5px 10px 10px">
                                                                        <c:if test="${films.genres.contains(genreModal)}">
                                                                            <button type="button" class="btn"
                                                                                    data-color="danger"
                                                                                    style="margin-bottom: 10px">#${genreModal.name}</button>
                                                                            <input type="checkbox" class="hidden"
                                                                                   name="genre"
                                                                                   value="${genreModal.name}" checked/>
                                                                        </c:if>
                                                                        <c:if test="${!films.genres.contains(genreModal)}">
                                                                            <button type="button" class="btn"
                                                                                    data-color="danger"
                                                                                    style="margin-bottom: 10px">#${genreModal.name}</button>
                                                                            <input type="checkbox" class="hidden"
                                                                                   name="genre"
                                                                                   value="${genreModal.name}"/>
                                                                        </c:if>
                                                                    </span>
                                                                </c:forEach>
                                                            </c:if>
                                                            <hr/>
                                                        </div>
                                                    </div>
                                                        <%--@elvariable id="directors" type="java.util.List"--%>
                                                        <%--@elvariable id="directorModal" type="com.katermar.movierating.entity.Director"--%>
                                                    <div class="form-group">
                                                        <label for="directorsSelect${films.idFilm}"><fmt:message
                                                                key="add.director"/></label>
                                                        <c:if test="${directorsModal ne null}">
                                                            <select class="form-control selectpicker show-tick"
                                                                    id="directorsSelect${films.idFilm}" name="director"
                                                                    data-style="btn-info">
                                                                <c:forEach items="${directorsModal}"
                                                                           var="directorModal">
                                                                    <c:if test="${films.director.iddirector eq directorModal.iddirector}">
                                                                        <option selected>${directorModal.name}</option>
                                                                    </c:if>
                                                                    <c:if test="${films.director.iddirector ne directorModal.iddirector}">
                                                                        <option>${directorModal.name}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="desc${films.idFilm}"><fmt:message
                                                                    key="add.film.desc"/></label>
                                                            <textarea class="form-control" id="desc${films.idFilm}"
                                                                      name="desc"
                                                                      rows="3" required>${films.description}</textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                    <fmt:message key="add.common.close"/>
                                                </button>
                                                <input type="hidden" name="mode" value="edit">
                                                <input type="hidden" name="id" value="${films.idFilm}">
                                                <button type="submit" class="btn btn-primary">
                                                    <fmt:message key="header.add"/>
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <%--modal--%>

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
</body>
</html>
