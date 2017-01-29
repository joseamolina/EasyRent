<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<t:basicpage title="Login">
<jsp:body>
	<h2>Access</h2>
	<form:form class="form-horizontal" method="post" modelAttribute="credentials"
		action="${pageContext.request.contextPath}/login.html">
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group">
      				<div class="input-group-addon"><span class="text-primary glyphicon glyphicon-user"></span></div>
					<form:input path="username" placeholder="username" class="form-control" />
					<form:errors path="username" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
      				<div class="input-group-addon"><span class="text-primary glyphicon glyphicon-lock"></span></div>
					<form:input type="password" path="pwd" placeholder="password" class="form-control" />
					<form:errors path="pwd" cssClass="error" />							
				</div>
			</div>	
		<a href="actor/add.html" class="btn btn-default" >Sign up</a>
		<input type="submit" class="btn btn-default" value="Access" /><br>
	</div>	
</form:form>
</jsp:body>
</t:basicpage>
