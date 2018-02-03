<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form>
    <input type="hidden" name="command" value="add-rating">
    <input type="hidden" name="id" value="${film.id}">
    <fieldset class="starRating">
        <!--HTML5 Custom Data Attributes (data-*)-->
        <c:choose>
            <c:when test="${avgRate eq null || avgRate < 1}">
                <input id="rating1" type="radio" class="star-rate" data-length="1" name="rating" value="1">
            </c:when>
            <c:when test="${avgRate >= 1}">
                <input id="rating1" class="star-rate checked" type="radio" data-length="1" name="rating" value="1">
            </c:when>
        </c:choose>
        <label for="rating1"></label>

        <c:choose>
            <c:when test="${avgRate >= 2 }">
                <input id="rating2" class="star-rate checked" data-length="2" type="radio" name="rating" value="2">
            </c:when>
            <c:otherwise>
                <input id="rating2" class="star-rate" data-length="2" type="radio" name="rating" value="2">
            </c:otherwise>
        </c:choose>
        <label for="rating2"></label>

        <c:choose>
            <c:when test="${avgRate >= 3}">
                <input id="rating3" class="star-rate checked" type="radio" data-length="3" name="rating" value="3">
            </c:when>
            <c:otherwise>
                <input id="rating3" class="star-rate" type="radio" data-length="3" name="rating" value="3">
            </c:otherwise>
        </c:choose>
        <label for="rating3"></label>

        <c:choose>
            <c:when test="${avgRate >= 4}">
                <input id="rating4" class="star-rate checked" data-length="4" type="radio" name="rating" value="4">
            </c:when>
            <c:otherwise>
                <input id="rating4" class="star-rate" data-length="4" type="radio" name="rating" value="4">
            </c:otherwise>
        </c:choose>
        <label for="rating4"></label>

        <c:choose>
            <c:when test="${avgRate eq 5}">
                <input id="rating5" class="star-rate checked" data-length="5" type="radio" name="rating" value="5">
            </c:when>
            <c:otherwise>
                <input id="rating5" class="star-rate" data-length="5" type="radio" name="rating" value="5">
            </c:otherwise>
        </c:choose>
        <label for="rating5"></label>
    </fieldset>
</form>
