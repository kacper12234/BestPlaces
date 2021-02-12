<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kacper.bestplaces.model.Rate" %>
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
<title><s:message code="menu.cat"/></title>
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
<body data-target="#navbarResponsive" onload="loading()">
<%@include file="/WEB-INF/incl/loading.app" %>
<div id="page">
<div id="bg">
<%@include file="/WEB-INF/incl/menu.app" %>
<c:set var="count" value="${recordStartCounter }"/>
<div id="placeslist" class="container">
<div class="row" style="padding: .1rem;">
<div class="input-group mb-4" style="max-width: 65%; padding-left: .8rem;">
            <input id="searchloc" type="search" placeholder="<s:message code="place.search"/>" aria-describedby="button-addon5" class="form-control">
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
          <div class="dropdown" style="padding-left: 1.5vw;">
<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<s:message code="place.sort"/>
</button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <c:choose>
          <c:when test="${not sort }">
          <a class="dropdown-item active"><s:message code="place.sort.rate"/></a>
          <c:choose>
          <c:when test="${empty type and empty search}">
          <a class="dropdown-item" href="/places/sortDate/1"><s:message code="place.sort.date"/></a>
          </c:when>
          <c:when test="${not empty type and empty search }">
           <a class="dropdown-item" href="/places/cat/${type}/sortDate/1"><s:message code="place.sort.date"/></a>
          </c:when>
          <c:when test="${not empty search and empty type}">
          <a class="dropdown-item" href="/places/search/${search}/sortDate/1"><s:message code="place.sort.date"/></a>
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <a class="dropdown-item" href="/places/cat/${type}/search/${search}/sortDate/1"><s:message code="place.sort.date"/></a>
          </c:when>
          </c:choose>
          </c:when>
          <c:otherwise>
          <a class="dropdown-item active"><s:message code="place.sort.date"/></a>
          <c:choose>
          <c:when test="${empty type and empty search}">
          <a class="dropdown-item" href="/places/1"><s:message code="place.sort.rate"/></a>
          </c:when>
          <c:when test="${not empty type and empty search }">
           <a class="dropdown-item" href="/places/cat/${type}/1"><s:message code="place.sort.rate"/></a>
          </c:when>
          <c:when test="${not empty search and empty type}">
          <a class="dropdown-item" href="/places/search/${search}/1"><s:message code="place.sort.rate"/></a>
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <a class="dropdown-item" href="/places/cat/${type}/search/${search}/1"><s:message code="place.sort.rate"/></a>
          </c:when>
          </c:choose>
          </c:otherwise>
          </c:choose>
          </div>
          </div>
          </div>
          <c:if test="${empty placesList }">
          <h1 align="center"><s:message code="place.notfound"/></h1>
          </c:if>
<c:forEach var="p" items="${placesList }">
<c:set var="count" value="${count+1 }" scope="page"/>
<div class="placeslist">
<a href="/places/${p.type }/${p.name }/1">
<div class="row">
<div class="col-md-4">
<img src="/resources/images/<c:out value="${p.name }"/>/<c:out value="${p.name }"/>1.jpg">
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
<p style="margin: 0 .5rem;"><c:out value="${p.getRate(Rate.LIKE) }"/></p>
<i class="fas fa-thumbs-down fa-lg" style="color: red;"></i>
<p style="margin: 0 .5rem;"><c:out value="${p.getRate(Rate.DISLIKE)  }"/></p>
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
                  <c:choose>
          <c:when test="${not sort }">
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/1'">
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/1'">
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/1'">
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/1'">
          </c:when>
          </c:choose>
          </c:when>
          <c:otherwise>
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/sortDate/1'">
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/sortDate/1'">
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/sortDate/1'">
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/sortDate/1'">
          </c:when>
          </c:choose>
          </c:otherwise>
          </c:choose>
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
    <c:choose>
    <c:when test="${totalPages <= 7 }">
    <c:forEach var="i" begin="1" end="${totalPages }">
    <c:choose>
    <c:when test="${i == currentPage }">
    <li class="page-item active"><a class="page-link">${i }</a></li>
    </c:when>
    <c:otherwise>
                  <c:choose>
          <c:when test="${not sort }">
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          </c:choose>
          </c:when>
          <c:otherwise>
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          </c:choose>
          </c:otherwise>
          </c:choose>
    </c:otherwise>
    </c:choose>
    </c:forEach>
    </c:when>
    <c:when test="${currentPage+7<totalPages}">
    <c:if test="${currentPage>1 }">
    <li class="page-item"><a class="page-link">...</a></li>
    </c:if>
     <c:forEach var="i" begin="${currentPage }" end="${currentPage+7 }">
    <c:choose>
    <c:when test="${i == currentPage }">
    <li class="page-item active"><a class="page-link">${i }</a></li>
    </c:when>
    <c:otherwise>
              <c:choose>
          <c:when test="${not sort }">
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          </c:choose>
          </c:when>
          <c:otherwise>
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          </c:choose>
          </c:otherwise>
          </c:choose>
    </c:otherwise>
    </c:choose>
    </c:forEach>
    <li class="page-item"><a class="page-link">...</a></li>
    </c:when>
    <c:otherwise>
    <c:if test="${currentPage>1 }">
    <li class="page-item"><a class="page-link">...</a></li>
    </c:if>
     <c:forEach var="i" begin="${currentPage }" end="${totalPages }">
    <c:choose>
    <c:when test="${i == currentPage }">
    <li class="page-item active"><a class="page-link">${i }</a></li>
    </c:when>
    <c:otherwise>
                 <c:choose>
          <c:when test="${not sort }">
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          </c:choose>
          </c:when>
          <c:otherwise>
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/sortDate/${i}'"><a class="page-link">${i }</a></li>
          </c:when>
          </c:choose>
          </c:otherwise>
          </c:choose>
    </c:otherwise>
    </c:choose>
    </c:forEach>
    </c:otherwise>
    </c:choose>
        <c:choose>
    <c:when test="${currentPage < totalPages }">
                      <c:choose>
          <c:when test="${not sort }">
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/${totalPages}'">
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/${totalPages}'">
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/${totalPages}'">
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/${totalPages}'">
          </c:when>
          </c:choose>
          </c:when>
          <c:otherwise>
          <c:choose>
          <c:when test="${empty type and empty search}">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/sortDate/${totalPages}'">
          </c:when>
          <c:when test="${not empty type and empty search }">
           <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/sortDate/${totalPages}'">
          </c:when>
          <c:when test="${not empty search and empty type}">
            <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/search/${search}/sortDate/${totalPages}'">
          </c:when>
          <c:when test="${not empty search and not empty type }">
          <li class="page-item"  onclick="window.location.href='${pageContext.request.contextPath}/places/cat/${type}/search/${search}/sortDate/${totalPages}'">
          </c:when>
          </c:choose>
          </c:otherwise>
          </c:choose>
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
</body>
</html>