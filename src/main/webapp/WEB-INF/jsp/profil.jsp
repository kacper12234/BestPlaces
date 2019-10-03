<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
 integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
 <link rel="stylesheet" type="text/css" href="/resources/css/newstyle.css" />
 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" 
    integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" 
    integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<title><s:message code="profil.userDane"/></title>
</head>
<body onload="loading()">
<%@include file="/WEB-INF/incl/loading.app" %>
<div id="page">
<%@include file="/WEB-INF/incl/menu.app" %>
<div class="container-fluid bg" id="profilbg">
<div class="row">
	<div class="col-md-4 col-sm-4 col-xs-12"></div>
		<div class="col-md-4 col-sm-4 col-xs-12">
<div style="padding-top: 5rem; text-align: center;">
	<h2><s:message code="profil.userDane"/></h2>
	<br></br>
      <p><b><s:message code="register.email"/></b></p>
      <p><c:out value="${user.email }"/></p>

      <p><b><s:message code="register.name"/></b></p>
      <p><c:out value="${user.name }"/></p>


      <p><b><s:message code="register.lastName"/></b></p>
      <p><c:out value="${user.lastName }"/></p>


    <p><b><s:message code="profil.rola"/></b></p>
    <p> <c:choose>
				<c:when test="${user.nrRoli == 1 }">
					<s:message code="word.admin"/>
				</c:when>
				<c:otherwise>
					<s:message code="word.user"/>
				</c:otherwise>
			</c:choose></p>

<div id="btn">
<button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/editprofil'"><s:message code="button.edycjaProfilu"/>
					</button>
					<button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/editpassword'"><s:message code="button.zmianaHasla"/>
	</button>
</div>
</div>
</div>
		<div class="col-md-4 col-sm-4 col-xs-12"></div>
			</div>
</div>
</div>
</body>
</html>