<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Registration">
<jsp:body>
	<div class="progress">
	<div class="progress-bar" role="progressbar" aria-valuenow="100"
		aria-valuemin="0" aria-valuemax="100" style="width:100%">
		Step 2: User credentials
	</div>
	</div>
	<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
	<h2>Enter credentials</h2>
	<form:form class="form" method="post" modelAttribute="newCredentials"
		action="${pageContext.request.contextPath}/credentials/add.html">
		<div class="col-md-3">
		<div class="form-group">
		<p>
			<form:label path="username">Username:</form:label>
			<form:input path="username" class="form-control"/>
			<form:errors path="username" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="pwd">Password:</form:label>
			<form:password path="pwd" class="form-control"/>
			<form:errors path="pwd" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<label class="radio-inline"><form:radiobutton path="role" value="owner" class="radio"/>Owner</label>
			<label class="radio-inline"><form:radiobutton path="role" value="tenant" class="radio"/>Tenant</label>
			<c:if test='${credentials != null && credentials.getRole().equals("administrator")}'>
			<label class="radio-inline"><form:radiobutton path="role" value="administrator" class="radio"/>Admin</label>
			</c:if>
		</p>
		</div>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/actor/add.html" >Back</a>
		<input type="submit" class="btn btn-default" value="Continue" />
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
