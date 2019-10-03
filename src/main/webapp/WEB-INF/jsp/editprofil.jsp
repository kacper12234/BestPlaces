<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
 integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
 <link rel="stylesheet" type="text/css" href="/resources/css/newstyle.css" />
<title><s:message code="profilEdit.pageName"/></title>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" 
    integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" 
    integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body onload="loading()">
<%@include file="/WEB-INF/incl/loading.app" %>
<div id="page">
<%@include file="/WEB-INF/incl/menu.app" %>
<div class="container-fluid bg" id="editcred">
<div class="row">
	<div class="col-md-4 col-sm-4 col-xs-12"></div>
		<div class="col-md-4 col-sm-4 col-xs-12">
<sf:form id="usersForm" class="form-container" action="updateprofil" modelAttribute="user"
		enctype="multipart/form-data" method="POST">
<h2 align="center" style="margin-top: 5rem;"><s:message code="profilEdit.pageName"/></h2>

		<sf:hidden path="id"/>
<div class="form-group">
<div><font color="red"><sf:errors path="name"/></font></div>
				<label id="input" for="InputName"><s:message code="register.name"/></label>
				<sf:input type="text" path="name" class="form-control" id="InputName"/>
			</div>

<div class="form-group">
<div><font color="red"><sf:errors path="lastName"/></font></div>
				<label id="input" for="InputLastName"><s:message code="register.lastName"/></label>
				<sf:input path="lastName"
						class="form-control" id="InputLastName" />
</div>

		<div class="form-group">
<div><font color="red"><sf:errors path="email"/></font></div>
				<label id="input" for="InputEmail"><s:message code="register.email"/></label>
				<sf:input path="email" class="form-control" id="InputEmail" />
</div>

			<div id="btn">
					<button type="submit" class="btn btn-success btn-lg"><s:message code="button.save"/></button>
					<button type="button" class="btn btn-secondary btn-lg" onclick="window.location.href='${pageContext.request.contextPath}/'">
					<s:message code="button.cancel"/></button>
</div>
	</sf:form>
</div>
<div class="col-md-4 col-sm-4 col-xs-12"></div>
</div>
</div>
</div>
</body>
</html>