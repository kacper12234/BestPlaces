<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <script type="text/javascript">
    <c:set var="p" value="${place }"/>
	function leave()
	{
		window.location='/places/${p.type }/${p.name }';
	}
	</script>
<title><s:message code="menu.mainPage"/></title>
</head>
<body data-target="#navbarResponsive">
<div id="bg">
<%@include file="/WEB-INF/incl/menu.app" %>
<c:if test="${fn:endsWith(requestScope['javax.servlet.forward.servlet_path'], '/like') or fn:endsWith(requestScope['javax.servlet.forward.servlet_path'], '/dislike')}">
<script>
	leave();
</script>
</c:if>
<div id="place" class="container">
<div class="col-12">
<h1><c:out value="${p.name }"/></h1>
<h3><c:out value="${p.loc }"/></h3>
</div>
<div class="col-sm-12">
<img src="/resources/images/<c:out value="${p.author }"/>/<c:out value="${p.link }"/>">
</div>
<div class="col-lg">
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
<p><c:out value="${p.descrp }"/>"</p>
</div>
</div>
<%@include file="/WEB-INF/incl/footer.app" %>
</div>
</body>
</html>