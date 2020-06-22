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
    <script type="text/javascript">
            	$(document).ready(function() { 
                $('#placeForm').on("submit", function(){
                    	$('.progress').css('display','block');         	
                });
            });
        </script> 
        <script>
        function takenr(nr)
        {
        	$('#nr').val(nr);
        }
        </script>
    <c:set var="p" value="${place }"/>
<title><c:out value="${p.name }"/></title>
</head>
<body data-target="#navbarResponsive" onload="loading()">
<%@include file="/WEB-INF/incl/loading.app" %>
<div id="page">
<div id="bg">
<%@include file="/WEB-INF/incl/menu.app" %>
<c:set var="count" value="${recordStartCounter }"/>
<div id="place" class="container">
<div class="col-12">
<h1><c:out value="${p.name }"/></h1>
<h3><c:out value="${p.loc }"/></h3>
</div>
<div class="col-sm-12">
<c:choose>
<c:when test="${p.count == 1 }">
<img src="/resources/images/<c:out value="${p.name }"/>/<c:out value="${p.name }"/>1.jpg">
</c:when>
<c:otherwise>
<div id="plimages" class="carousel slide">
	<ol class="carousel-indicators">
		<li data-target="#plimages" data-slide-to="0" class="active"/>
		<c:forEach var="i" begin="1" end="${p.count-1 }">
		<li data-target="#plimages" data-slide-to="${i }" />		
		</c:forEach>
	</ol>
	<div class="carousel-inner" role="listbox">
	<div class="carousel-item active">
		<img src="/resources/images/<c:out value="${p.name }"/>/<c:out value="${p.name }"/>1.jpg">
		<c:choose>
		<c:when test="${user == p.author }">
		<div class="content">
		<a href="#usure2" data-toggle="modal" data-target="#usure2" onclick="takenr(1)"><i class="fas fa-trash-alt fa-2x"></i></a>
		</div>
		</c:when>
		<c:otherwise>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div class="content">
		<a href="#usure2" data-toggle="modal" data-target="#usure2" onclick="takenr(1)"><i class="fas fa-trash-alt fa-2x"></i></a>
		</div>
		</sec:authorize>
		</c:otherwise>
		</c:choose>
	</div>
	<c:forEach var="i" begin="2" end="${p.count }">
	<div class="carousel-item">
		<img src="/resources/images/<c:out value="${p.name }"/>/<c:out value="${p.name }"/>${i }.jpg">
		<c:choose>
		<c:when test="${user == p.author }">
		<div class="content">
		<a href="#usure2" data-toggle="modal" data-target="#usure2" onclick="takenr(${i})"><i class="fas fa-trash-alt fa-2x"></i></a>
		</div>
		</c:when>
		<c:otherwise>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div class="content">
		<a href="#usure2" data-toggle="modal" data-target="#usure2" onclick="takenr(${i})"><i class="fas fa-trash-alt fa-2x"></i></a>
		</div>
		</sec:authorize>
		</c:otherwise>
		</c:choose>
	</div>
	</c:forEach>
	</div>
	</div>
</c:otherwise>
</c:choose>
</div>
<div class="col-lg">
<div class="row">
<div class="col-auto">
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
<div class="col" style="text-align: right;">
<c:choose>
<c:when test="${user == p.author }">
<div class="dropdown">
<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<s:message code="place.tool"/>
</button>
 <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
  <a class="dropdown-item" href="/places/${p.author }/${p.name }/edit">
 <s:message code="place.mod"/>
</a>
 <a class="dropdown-item" href="#usure" data-toggle="modal" data-target="#usure">
 <s:message code="place.del"/>
</a>
<a class="dropdown-item" href="#addphoto"  data-toggle="modal" data-target="#addphoto">
<s:message code="place.addphoto"/>
</a>
</div>
</div>
</c:when>
<c:otherwise>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<div class="dropdown">
<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<s:message code="place.tool"/>
</button>
 <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
  <a class="dropdown-item" href="/places/${p.author }/${p.name }/edit">
 <s:message code="place.mod"/>
</a>
<a class="dropdown-item" href="#usure" data-toggle="modal" data-target="#usure">
 <s:message code="place.del"/>
</a>
</div>
</div>
</sec:authorize>
</c:otherwise>
</c:choose>
</div>
</div>
<p><c:out value="${p.descrp }"/></p>
<p><s:message code="place.author"/><c:out value="${username }"/></p>
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
<c:choose>
<c:when test="${empty text.comment}">
<button type="submit" class="btn btn-success btn-lg">
<s:message code="menu.add"/>
</button>
</c:when>
<c:otherwise>
<div class="row">
<button id="bcom" type="submit" class="btn btn-success btn-lg">
<s:message code="comment.mod"/>
</button>
<button id="bcom" type="button" class="btn btn-danger btn-lg" onclick="window.location.href='${pageContext.request.contextPath}/places/${p.type }/${p.name }/delcom'">
<s:message code="comment.del"/>
</button>
</div>
</c:otherwise>
</c:choose>
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
<div class="modal" id="addphoto" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><s:message code="place.modal.photo"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
     <sf:form class="form"
	action="phsend" method="POST" modelAttribute="fileupload" enctype="multipart/form-data">
      <div class="modal-body">
      <div class="form-group">
      <label class="custom-file-label" for="inputImage"><s:message code="addplace.link"/></label>
 <input type="file" name="filename[]" accept=".jpg" class="custom-file-input" id="inputImage" multiple/>
		</div>
		<div class="progress">
  <div id="progressBar" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="100" aria-valuemin="100" aria-valuemax="100" style="width: 100%;"><s:message code="file.upload.show"/></div>
</div>
      </div>
      <div class="modal-footer">
    <button class="btn btn-primary" type="submit"><s:message code="menu.add"/></button>
  </div>
  </sf:form>
    </div>
  </div>
</div>
<div class="modal" id="usure" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><s:message code="place.approve"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <p><s:message code="place.approve1"/></p>
      </div>
      <div class="modal-footer">
    <a class="btn btn-primary" href="/places/${p.author }/${p.name }/delete"><s:message code="word.tak"/></a>
    <button class="btn btn-danger" data-dismiss="modal"><s:message code="word.nie"/></button>
  </div>
    </div>
  </div>
</div>
<div class="modal" id="usure2" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><s:message code="place.approve"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <p><s:message code="place.approve2"/></p>
      </div>
      <div class="modal-footer">
      <sf:form action="delph">
      <input type="hidden"  id="nr" name="photonr"/>
    <button class="btn btn-primary" type="submit"><s:message code="word.tak"/></button>
    </sf:form>
    <button class="btn btn-danger" data-dismiss="modal"><s:message code="word.nie"/></button>
  </div>
    </div>
  </div>
</div>
<!-- <div class="ad">
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
</div>
<%@include file="/WEB-INF/incl/footer.app" %>
</div>
			    <script type="text/javascript">
$(".custom-file-input").on("change", function() {
  var count=$(this)[0].files.length;
  if(count>1)
	  var fileName=count+' files selected';
  else
	  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
</script>
</body>
</html>