<div class="modal" id="contact" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><s:message code="msg.desc"/></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <sf:form class="form" action="/send" modelAttribute="email" method="POST">
      <div class="modal-body">
      <div class="form-group">
      <label id="input" for="SelectType"><s:message code="msg.type"/></label>
		<sf:select class="custom-select" type="text" path="type" id="SelectType">
    <option selected></option>
    <option value="<s:message code="msg.cat1"/>"><s:message code="msg.cat1"/></option>
    <option value="<s:message code="msg.cat2"/>"><s:message code="msg.cat2"/></option>
  </sf:select>
  </div>
   <div class="form-group">
        <label id="input" for="InputDescrp"><s:message code="msg.text"/></label>
		<sf:textarea type="text" path="msg" class="form-control" id="InputDescrp"/>
		</div>
      </div>
      <div class="modal-footer">
    <button class="btn btn-primary" type="submit"><s:message code="msg.send"/></button>
  </div>
  </sf:form>
    </div>
  </div>
</div>

<div id="contact">
<footer>
<div class="row justify-content-center">
<div class="col-md-5 text-center">
<h1><s:message code="menu.name"/></h1>
<strong><s:message code="contact.contact"/></strong>
<sec:authorize access="hasRole('ANONYMOUS')">
<p>kacpermochniej1999@gmail.com</p>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
<p><a href="#contact" data-toggle="modal" data-target="#contact">kacpermochniej1999@gmail.com</a></p>
</sec:authorize>
</div>
<hr class="socket">
&copy;KACPER
</div>
</footer>
</div>