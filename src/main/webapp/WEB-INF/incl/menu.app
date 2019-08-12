<nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
	<a class="navbar-brand" href="/"><s:message code="menu.name"/></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target=
	"#navbarResponsive">
	<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarResponsive">
		<ul class="navbar-nav mr-auto">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
			<li class="nav-item">
				<a href="/admin/users/1"><s:message code="menu.adminPage"/></a>
				</li>
			</sec:authorize>
			</ul>
			<ul class="navbar-nav">
			 <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" id="cat" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <s:message code="menu.cat"/></a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
        <a class="dropdown-item" href="/places/1"><s:message code="menu.cata"/></a>
          <a class="dropdown-item" href="/places/Natura/1"><s:message code="menu.cat1"/></a>
          <a class="dropdown-item" href="/places/Restauracje/1"><s:message code="menu.cat2"/></a>
          <a class="dropdown-item" href="/places/Zabytki/1"><s:message code="menu.cat3"/></a>
        </div>
      </li>
		<sec:authorize access="hasRole('ANONYMOUS')">
		<li class="nav-item">
			<a href="/login"><s:message code="menu.login"/></a>
			</li>
			<li class="nav-item">
			<a href="/register"><s:message code="menu.register"/></a>
			</li>
		</sec:authorize>
		
		<sec:authorize access="isAuthenticated()">
		<li class="nav-item">
			<a href="/addplace"><s:message code="menu.add"/></a>
			</li>
		<li class="nav-item">
			<a href="/profil"><s:message code="menu.profil"/></a>
			</li>
			<li class="nav-item">
			<a href="/logout"><s:message code="menu.logout"/></a>
			</li>	
		</sec:authorize>
</ul>
</div>
</nav>