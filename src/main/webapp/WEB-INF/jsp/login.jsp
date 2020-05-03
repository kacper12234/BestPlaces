<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" http-equiv="Content-Type" content="text/html; charset=UTF-8; width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
 integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
 <link rel="stylesheet" type="text/css" href="/resources/css/newstyle.css" />
<title><s:message code="logowanie.pageName" /></title>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" 
    integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" 
    integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body data-target="#navbarResponsive" onload="loading()">
<%@include file="/WEB-INF/incl/loading.app" %>
<div id="page">
	<%@include file="/WEB-INF/incl/menu.app"%>
<div class="container-fluid bg" id="loginbg">
	<div class="row">
		<div class="col-md-4 col-sm-4 col-xs-12"></div>
		<div class="col-md-4 col-xl-4 col-xs-12">
		<form class="form-container" action="/login" method="POST">
		<h1 id="loginName"><s:message code="logowanie.pageName"/></h1>
		<div class="form-group">
		<c:if test="${not empty param.error}">
					<div><font color="red"><s:message code="error.logowanie"/></font></div>
					</c:if>
		<label id="input" for="InputEmail"><s:message code="logowanie.email"/></label>
		<input type="text" name="email" class="form-control" id="InputEmail">
		</div>
		<div class="form-group">
		<label id="input" for="InputPassword"><s:message code="logowanie.password"/></label>
		<input type="password" name="password" class="form-control" id="InputPassword">
		</div>
		<a href="/resetpass"><s:message code="pass.forgot"/></a>
		<button type="submit" class="btn btn-success btn-block"><s:message code="logowanie.submit"/></button>
		</form>
		</div>
		<div class="col-md-4 col-sm-4 col-xs-12"></div>
			</div>
		</div>
	</div>
</body>
</html>