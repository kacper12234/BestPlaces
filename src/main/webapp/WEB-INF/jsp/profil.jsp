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
<body>
<%@include file="/WEB-INF/incl/menu.app" %>
<div class="container-fluid bg" id="profilbg">
<div align="center" style="padding-top: 5rem;">
	<h2><s:message code="profil.userDane"/></h2>
</div>

<table width="500" border="0" cellpadding="4" cellspacing="1" align="center">

	<tr>
		<td width="130" align="right" >
			<s:message code="register.email"/>
		</td>
		<td width="270" align="left">
			<c:out value="${user.email }"/>
		</td>
	</tr>
	
	<tr>
		<td width="130" align="right" >
			<s:message code="register.name"/>
		</td>
		<td width="270" align="left">
			<c:out value="${user.name }"/>
		</td>
	</tr>
	
	<tr>
		<td width="130" align="right" >
			<s:message code="register.lastName"/>
		</td>
		<td width="270" align="left">
			<c:out value="${user.lastName }"/>
		</td>
	</tr>
	
	<tr>
		<td width="130" align="right" >
			<s:message code="profil.czyAktywny"/>
		</td>
		<td width="270" align="left">
			<c:choose>
				<c:when test="${user.active == 1 }">
					<s:message code="word.tak"/>
				</c:when>
				<c:otherwise>
					<s:message code="word.nie"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	
	<tr>
		<td width="130" align="right" >
			<s:message code="profil.rola"/>
		</td>
		<td width="270" align="left">
			<c:choose>
				<c:when test="${user.nrRoli == 1 }">
					<s:message code="word.admin"/>
				</c:when>
				<c:otherwise>
					<s:message code="word.user"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

</table>

<table width="500" border="0" cellpadding="4" cellspacing="1" align="center">

<tr>
		<td align="center">
			<input type="button" value="<s:message code="button.edycjaProfilu"/>" 
					onclick="window.location.href='${pageContext.request.contextPath}/editprofil'"/>
		</td>
	<td align="center">
	<input type="button" value="<s:message code="button.zmianaHasła"/>"
	onclick="window.location.href='${pageContext.request.contextPath}/editPassword'"/>
	</td>
</tr>

</table>
</div>
</body>
</html>