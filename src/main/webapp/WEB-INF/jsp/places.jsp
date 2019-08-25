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
<script type="text/javascript">
function startSearch()
{
	var searchWord=document.getElementById('searchloc').value;
		var searchLink='${pageContext.request.contextPath}/places/search/'+searchWord+'/1';
		window.location.href=searchLink;
}
function startSearchType()
{
	var searchWord=document.getElementById('searchloc').value;
		var searchLink='${pageContext.request.contextPath}/places/cat/${type}/search/'+searchWord+'/1';
		window.location.href=searchLink;
}
</script>
</head>
<body data-target="#navbarResponsive">
<div id="bg">
<%@include file="/WEB-INF/incl/menu.app" %>
<c:set var="count" value="${recordStartCounter }"/>
<div id="placeslist" class="container">
<div class="input-group mb-4">
            <input id="searchloc" type="search" placeholder="<s:message code="places.search"/>" aria-describedby="button-addon5" class="form-control">
            <div class="input-group-append">
            <c:choose>
            <c:when test="${empty type}">
              <button id="button-addon5" class="btn btn-primary" onclick="startSearch();"><i class="fa fa-search text-grey"></i></button>
              </c:when>
              <c:otherwise>
               <button id="button-addon5" class="btn btn-primary" onclick="startSearchType();"><i class="fa fa-search text-grey"></i></button>
              </c:otherwise>
              </c:choose>
            </div>
          </div>
          <c:if test="${empty placesList }">
          <h1 align="center"><s:message code="places.notfound"/></h1>
          </c:if>
<c:forEach var="p" items="${placesList }">
<c:set var="count" value="${count+1 }" scope="page"/>
<div class="placeslist">
<a href="/places/${p.type }/${p.name }/1">
<div class="row">
<div class="col-md-4">
<img src="/resources/images/<c:out value="${p.author }"/>/<c:out value="${p.link }"/>">
</div>
<div class="col-md">
<div class="row">
<div class="col-md">
<h2><c:out value="${p.name }"/></h2>
<h4><c:out value="${p.loc }"/></h4>
</div>
<div class="col-md-3">
<div class="row" style="padding: .5rem 1rem; justify-content:right; flex-wrap:nowrap;">
<i class="fas fa-thumbs-up fa-lg" style="color: green;"></i>
<p style="margin: 0 .5rem;"><c:out value="${p.up }"/></p>
<i class="fas fa-thumbs-down fa-lg" style="color: red;"></i>
<p style="margin: 0 .5rem;"><c:out value="${p.down }"/></p>
</div>
</div>
</div>
<div class="col-md">
</div>
</div>
</div>
</a>
</div>
</c:forEach>
<c:if test="${not empty placesList }">
<div id="pages">
<ul class="pagination">
    <c:choose>
    <c:when test="${currentPage > 1 }">
    <li class="page-item" onclick="window.location.href='${pageContext.request.contextPath}/places/${currentPage - 1}'">
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
    <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/${currentPage + 1}'">
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
</div>
<%@include file="/WEB-INF/incl/footer.app" %>
</body>
</html>