<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<script src="${pageContext.request.contextPath}/js/checkbox.js"></script>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="search-films">
    <div class="container">
        <%--@elvariable id="genres" type="java.util.List"--%>
        <%--@elvariable id="director" type="com.katermar.movierating.entity.Genre"--%>
        <div class="btn-group btn-group-toggle col-md-12 col-sm-12 col-xs-12" data-toggle="buttons"
             style="display: flex">
            <c:if test="${genres ne null}">
                <c:forEach items="${genres}" var="genre">
                    <span class="button-checkbox" style="margin: 10px 5px 10px 10px">
                    <button type="button" class="btn" data-color="danger">#${genre.name}</button>
                    <input type="checkbox" class="hidden" name="genre" value="${genre.name}"/>
                 </span>
                </c:forEach>
            </c:if>
            <hr/>
        </div>
        <%--@elvariable id="directors" type="java.util.List"--%>
        <%--@elvariable id="director" type="com.katermar.movierating.entity.Director"--%>
        <div class="btn-group btn-group-toggle col-md-12 col-sm-12 col-xs-12" data-toggle="buttons"
             style="display: flex">
            <c:if test="${directors ne null}">
                <c:forEach items="${directors}" var="director">
                    <span class="button-checkbox" style="margin: 10px 5px 10px 10px">
                    <button type="button" class="btn" data-color="warning">#${director.name}</button>
                    <input type="checkbox" class="hidden" name="director" value="${director.name}"/>
                </span>
                </c:forEach>
            </c:if>
            <hr/>
        </div>
        <div class="col-md-6 col-sm-6 col-xs-6">
            <fmt:message key="search.year"/> :
            <input type="number" min="1900" max="2018" name="min-year" placeholder="from">
            <input type="number" min="1900" max="2018" name="max-year" placeholder="to">
        </div>
        <hr/>
        <div class="col-md-6 col-sm-6 col-xs-6">
            <fmt:message key="film.info.duration"/> :
            <input type="number" min="0" max="500" name="min-duration" placeholder="from">
            <input type="number" min="0" max="500" name="max-duration" placeholder="to">
        </div>

        <button type="submit" style="width: auto">submit</button>
    </div>
</form>
