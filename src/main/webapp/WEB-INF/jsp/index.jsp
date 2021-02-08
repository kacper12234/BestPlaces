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
<title><s:message code="menu.mainPage"/></title>
</head>
<body data-target="#navbarResponsive" onload="loading()">
<%@include file="/WEB-INF/incl/loading.app" %>
<div id="page">
<div id="home">
<%@include file="/WEB-INF/incl/menu.app" %>

<div class="modal" id="regSuccess" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><s:message code="modal.success"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p><c:out value="${message }"/></p>
      </div>
    </div>
  </div>
</div>

<c:if test="${not empty message }">
<script>
$('#regSuccess').modal()
</script>
</c:if>

<!-- image slider -->
<div id="bgimages" class="carousel slide" data-ride="carousel" data-interval="7000">
	<ol class="carousel-indicators">
		<li data-target="#bgimages" data-slide-to="0" class="active"/>
		<li data-target="#bgimages" data-slide-to="1" />
		<li data-target="#bgimages" data-slide-to="2" />
	</ol>
	<div class="carousel-inner" role="listbox">
		<div class="carousel-item active" style="background-image: url(/resources/images/sunrise.jpg);">
		<div class="carousel-caption text-center">
		<h1><s:message code="menu.title"/></h1>
		<h3><s:message code="menu.desc"/></h3>
		<sec:authorize access="hasRole('ANONYMOUS')">
		<a class="btn btn-outline-light btn-lg" href="/register"><s:message code="menu.begin"/></a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
		<a class="btn btn-outline-light btn-lg" href="/addplace"><s:message code="menu.add2"/></a>
		</sec:authorize>
		</div>
		</div>
		<div class="carousel-item" style="background-image: url(/resources/images/cafe.jpg);">
		<div class="carousel-caption text-center">
		<h1><s:message code="menu.desc2"/></h1>
		</div>
		</div>
		<div class="carousel-item" style="background-image: url(/resources/images/street.jpg);">
		<div class="carousel-caption text-center">
		<h1><s:message code="menu.desc3"/></h1>
		</div>
		</div>
	</div>
	<a class="carousel-control-prev" href="#bgimages" role="button"
	data-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span>
	</a>
	<a class="carousel-control-next" href="#bgimages" role="button"
	data-slide="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span>
	</a>
</div> <!-- End image slider -->
</div><!-- End home section -->
<div id="newplaces">
<div class="jumbotron container-fluid">
<div class="col-12 text-center">
	<h3 class="heading"><s:message code="menu.heading"/></h3>
	<div class="heading-underline"></div>
	</div>
	<div class="row no-padding">
	<c:forEach var="p" items="${randomPlaces }">
	<div class="col-sm-4">
	<div class="newplaces">
<a href="/places/${p.type }/${p.name }/1">
	<img src="/resources/images/<c:out value="${p.name }"/>/<c:out value="${p.name }"/>1.jpg">
	<div class="content">
	<h2><c:out value="${p.name }"/></h2>
	<h4><c:out value="${p.loc }"/></h4>
	</div>
	</a>
	</div>
	</div>
	</c:forEach>
	</div>
	<div class="narrow text-center">
		<div class="col-12">
			<p class="lead">
			<s:message code="index.notsomuch"/>
			</p>
			<a class="btn btn-secondary btn-md" href="/places/1">
			<s:message code="index.watchmore"/>
			</a>
		</div>
	</div>
</div> <!-- End jumbotron -->
<!--  <div class="ad">
<h3><s:message code="ad"/></h3>
	 <script type="text/javascript">
	atOptions = {
		'key' : 'd3372883c2e0010919ffc443be2e2547',
		'format' : 'iframe',
		'height' : 90,
		'width' : 728,
		'params' : {}
	};
	document.write('<scr' + 'ipt type="text/javascript" src="http' + (location.protocol === 'https:' ? 's' : '') + '://www.bcloudhost.com/d3372883c2e0010919ffc443be2e2547/invoke.js"></scr' + 'ipt>');
</script>
</div> -->
</div> <!-- End newplaces section -->
<%@include file="/WEB-INF/incl/footer.app" %>
</div>
</body>
</html>