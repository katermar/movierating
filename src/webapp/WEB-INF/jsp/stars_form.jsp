<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 1/15/2018
  Time: 10:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="/controller" method="post">
    <input type="hidden" name="command" value="add-rating">
    <input type="hidden" name="id" value="${film.idFilm}">
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
                       onchange="this.form.submit()">
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
