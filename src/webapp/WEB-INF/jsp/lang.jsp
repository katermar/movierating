<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/languages.min.css">
<link rel="stylesheet" href="/css/lang.css">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <c:if test="${sessionScope.locale eq 'ru_RU'}">
            <!-- Single button -->
            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <span class="lang-sm lang-lbl" lang="ru"></span><span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="/controller?command=switch-language"><span class="lang-sm lang-lbl"
                                                                            lang="en"></span></a></li>
                </ul>
            </div>
        </c:if>
        <c:if test="${sessionScope.locale eq 'en_US'}">
            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <span class="lang-sm lang-lbl" lang="en"></span><span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="/controller?command=switch-language"><span class="lang-sm lang-lbl"
                                                                            lang="ru"></span></a></li>
                </ul>
            </div>
        </c:if>
    </c:when>
    <c:otherwise>
        <div class="btn-group">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                <span class="lang-sm lang-lbl" lang="en"></span><span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="/controller?command=switch-language"><span class="lang-sm lang-lbl" lang="ru"></span></a>
                </li>
            </ul>
        </div>
    </c:otherwise>
</c:choose>
