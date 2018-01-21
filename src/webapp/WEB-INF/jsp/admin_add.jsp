<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
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
<c:import url="header.jsp"/>
<head>
    <title><fmt:message key="header.add"/> | Movierating</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png"/>
    <script src="${pageContext.request.contextPath}/js/checkbox.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
</head>

<body>
<!-- Button trigger modal -->
<br/>
<div class="well col-md-8 col-md-offset-2">
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalGenre">
        <fmt:message key="add.genre"/>
    </button>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalDirector">
        <fmt:message key="add.director"/>
    </button>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalFilm">
        <fmt:message key="add.film"/>
    </button>
</div>

<!-- Modal -->
<div class="modal fade" id="modalGenre" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="add-genre">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="container-fluid">
                        <div class="row">
                            <button type="button col-md-1 pull-right" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title col-md-11 text-center" id="modalGenreTitle"><fmt:message
                                    key="header.add"/> <fmt:message key="add.genre"/></h3>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="form-group">
                            <label for="filmName"><fmt:message key="add.director.name"/></label>
                            <input class="form-control" type="text" placeholder="name" id="genreName" name="name"
                                   required>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                            key="add.common.close"/></button>
                    <button type="submit" class="btn btn-primary"><fmt:message key="header.add"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal fade" id="modalDirector" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="add-director">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="container-fluid">
                        <div class="row">
                            <button type="button col-md-1 pull-right" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title col-md-11 text-center" id="modalDirectorTitle"><fmt:message
                                    key="header.add"/> <fmt:message key="add.director"/></h3>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="form-group">
                            <label for="filmName"><fmt:message key="add.director.name"/></label>
                            <input class="form-control" type="text" placeholder="name" id="directorName" name="name"
                                   required>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                            key="add.common.close"/></button>
                    <button type="submit" class="btn btn-primary"><fmt:message key="header.add"/></button>
                </div>
            </div>
        </form>
    </div>

</div>
<div class="modal fade" id="modalFilm" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="add-film">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="container-fluid">
                        <div class="row">
                            <button type="button col-md-1 pull-right" class="close" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title col-md-11 text-center" id="modalFilmTitle"><fmt:message
                                    key="header.add"/> <fmt:message key="add.film"/></h3>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="form-group">
                            <label for="filmName"><fmt:message key="add.film.name"/></label>
                            <input class="form-control" type="text" placeholder="name" id="filmName" name="name"
                                   required>
                        </div>
                        <div class="form-group">
                            <label for="year"><fmt:message key="add.film.releaseYear"/></label>
                            <input class="form-control" type="number" placeholder="release year" id="year" name="year"
                                   max="2018"
                                   min="1900" required>
                        </div>
                        <div class="form-group">
                            <label for="duration"><fmt:message key="add.film.duration"/></label>
                            <input class="form-control" type="number" placeholder="duration" id="duration"
                                   name="duration"
                                   min="1"
                                   required>
                        </div>
                        <div class="form-group">
                            <label for="poster"><fmt:message key="add.film.poster"/></label>
                            <input class="form-control" type="url" name="poster" id="poster" placeholder="poster"
                                   pattern="^(https|http).+(jpg|svg|gif|png)$">
                        </div>
                        <%--@elvariable id="genres" type="java.util.List"--%>
                        <%--@elvariable id="director" type="com.katermar.movierating.entity.Genre"--%>
                        <div class="form-group">
                            <label><fmt:message key="add.genre"/></label>
                            <div class="btn-group btn-group-toggle col-md-12 col-sm-12 col-xs-12 text-center"
                                 data-toggle="buttons">
                                <c:if test="${genres ne null}">
                                    <c:forEach items="${genres}" var="genre">
                                    <span class="button-checkbox" style="margin: 10px 5px 10px 10px">
                                        <button type="button" class="btn" data-color="danger"
                                                style="margin-bottom: 10px">#${genre.name}</button>
                                        <input type="checkbox" class="hidden" name="genre" value="${genre.name}"/>
                                    </span>
                                    </c:forEach>
                                </c:if>
                                <hr/>
                            </div>
                        </div>
                        <%--@elvariable id="directors" type="java.util.List"--%>
                        <%--@elvariable id="director" type="com.katermar.movierating.entity.Director"--%>
                        <div class="form-group">
                            <label for="directorsSelect"><fmt:message key="add.director"/></label>
                            <c:if test="${directors ne null}">
                                <select class="form-control selectpicker show-tick" id="directorsSelect" name="director"
                                        data-style="btn-info">
                                    <c:forEach items="${directors}" var="director">
                                        <option>${director.name}</option>
                                    </c:forEach>
                                </select>
                            </c:if>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label for="desc"><fmt:message key="add.film.desc"/></label>
                                <textarea class="form-control" id="desc" name="desc" rows="3" required></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                            key="add.common.close"/></button>
                    <button type="submit" class="btn btn-primary"><fmt:message key="header.add"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
