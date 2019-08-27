<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="text/html; charset=UTF-8; width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
 integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
 <link rel="stylesheet" type="text/css" href="/resources/css/newstyle.css" />
 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" 
    integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" 
    integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/3c44ec830b.js"></script>
<title><s:message code="menu.mainPage"/></title>
</head>
<body data-target="#navbarResponsive">
<div id="bg">
<%@include file="/WEB-INF/incl/menu.app" %>
<c:set var="count" value="${recordStartCounter }"/>
<div id="place" class="container">
<c:set var="p" value="${place }"/>
<div class="col-12">
<h1><c:out value="${p.name }"/></h1>
<h3><c:out value="${p.loc }"/></h3>
</div>
<div class="col-sm-12">
<img src="/resources/images/<c:out value="${p.author }"/>/<c:out value="${p.link }"/>">
</div>
<div class="col-lg">
<div class="row">
<div class="col">
<div class="row" style="margin: .5rem; ">
<sec:authorize access="isAuthenticated()">
<c:choose>
<c:when test="${like == 1 }">
<a  href="/places/${p.type }/${p.name }/like">
<i class="fas fa-thumbs-up fa-2x" style="color: blue;"></i></a>
</c:when>
<c:otherwise>
<a  href="/places/${p.type }/${p.name }/like">
<i class="fas fa-thumbs-up fa-2x" style="color: green;"></i></a>
</c:otherwise>
</c:choose>
</sec:authorize>
<sec:authorize access="hasRole('ANONYMOUS')">
<i class="fas fa-thumbs-up fa-2x" style="color: green;"></i>
</sec:authorize>
<h5 style="margin: .7rem"><c:out value="${p.up }"/></h5>
<sec:authorize access="isAuthenticated()">
<c:choose>
<c:when test="${like == 2 }">
<a href="/places/${p.type }/${p.name }/dislike">
<i class="fas fa-thumbs-down fa-2x" style="color: blue;"></i></a>
</c:when>
<c:otherwise>
<a href="/places/${p.type }/${p.name }/dislike">
<i class="fas fa-thumbs-down fa-2x" style="color: red;"></i></a>
</c:otherwise>
</c:choose>
</sec:authorize>
<sec:authorize access="hasRole('ANONYMOUS')">
<i class="fas fa-thumbs-down fa-2x" style="color: red;"></i>
</sec:authorize>
<h5 style="margin: .7rem"><c:out value="${p.down }"/></h5>
</div>
</div>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<div class="col-2" style="text-align: right;">
<a href="/places/${p.type }/${p.name }/delete">
<i class="fas fa-times fa-3x" style="color: red;"></i></a>
</div>
</sec:authorize>
</div>
<p><c:out value="${p.descrp }"/></p>
<p><s:message code="place.author"/><c:out value="${p.author }"/></p>
</div>
</div>
<div id="comment" class="container">
<sec:authorize access="isAuthenticated()">
<sf:form class="form" action="/places/${p.type}/${p.name}/addcom" modelAttribute="text" method="POST">
<div class="form-group">
<label for="mycom">
<c:choose>
<c:when test="${empty text.comment}">
<s:message code="comment.label.add"/>
</c:when>
<c:otherwise>
<s:message code="comment.label.mod"/>
</c:otherwise>
</c:choose>
</label>
<sf:textarea path="comment" type="text" class="form-control" id="mycom"/>
</div>
<button type="submit" class="btn btn-success btn-lg">
<c:choose>
<c:when test="${empty text.comment}">
<s:message code="menu.add"/>
</c:when>
<c:otherwise>
<s:message code="comment.mod"/>
</c:otherwise>
</c:choose>
</button>
</sf:form>
</sec:authorize>
<sec:authorize access="hasRole('ANONYMOUS')">
<h3><s:message code="place.anonim"/></h3>
</sec:authorize>
<c:forEach var="r" items="${rev }">
<c:set var="count" value="${count+1 }" scope="page"/>
<div class="comment">
<div class="row">
<div class="col">
<h5><c:out value="${r.user }"/></h5>
</div>
<div class="col" style="text-align: right;">
<c:if test="${r.likes == 1 }">
<i class="fas fa-thumbs-up" style="color: green;"></i>
</c:if>
<c:if test="${r.likes == 2 }">
<i class="fas fa-thumbs-down" style="color: red;"></i>
</c:if>
</div>
</div>
<div class="col" style="padding: 0;">
<c:out value="${r.comment }"/>
</div>
</div>
</c:forEach>
<c:if test="${not empty rev }">
<div id="pages">
<ul class="pagination">
    <c:choose>
    <c:when test="${currentPage > 1 }">
    <li class="page-item" onclick="window.location.href='${pageContext.request.contextPath}/places/${p.type }/${p.name }/${currentPage - 1}'">
     <a class="page-link" aria-label="Previous">
     <span aria-hidden="true">&laquo;</span>
      </a>
      </li>
    </c:when>
    <c:otherwise>
    <li class="page-item disabled">
     <a class="page-link" aria-label="Previous" style="background-color: #eceef0!important;
	border: .01rem solid #7b9dda;">
     <span aria-hidden="true">&laquo;</span>
      </a>
      </li>
    </c:otherwise>
    </c:choose>
    <c:forEach var="i" begin="1" end="${totalPages }">
    <c:choose>
    <c:when test="${i == currentPage }">
    <li class="page-item active"><a class="page-link">${i }</a></li>
    </c:when>
    <c:otherwise>
    <li class="page-item"><a class="page-link">${i }</a></li>
    </c:otherwise>
    </c:choose>
    </c:forEach>
    <c:choose>
    <c:when test="${currentPage < totalPages }">
    <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/${p.type }/${p.name }/${currentPage + 1}'">
    <a class="page-link"  aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
      </li>
      </c:when>
      <c:otherwise>
      <li class="page-item disabled">
      <a class="page-link"  aria-label="Next" style="background-color: #eceef0!important;
	border: .01rem solid #7b9dda;">
        <span aria-hidden="true">&raquo;</span>
      </a>
      </li>
      </c:otherwise>
    </c:choose>
  </ul>
</div>
</c:if>
</div>
<%@include file="/WEB-INF/incl/footer.app" %>
</div>
</body>
</html>