<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Edit credentials">
<jsp:body>
	<h2>User credentials data</h2>
	<form:form class="form" method="post" modelAttribute="credentials">
	<div class="col-md-3">
		<div class="form-group">
		<p>
			<form:label path="username">Username:</form:label>
			<form:input path="username" readonly="true" class="form-control"/>
			<form:errors path="username" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<label class="radio-inline"><form:radiobutton path="role" value="owner" class="radio"/>Owner</label>
			<label class="radio-inline"><form:radiobutton path="role" value="tenant" class="radio"/>Tenant</label>
			<label class="radio-inline"><form:radiobutton path="role" value="administrator" class="radio"/>Admin</label>
		</p>
		</div>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/credentials/list.html" >Cancel</a>
		<input type="submit" class="btn btn-default" value="Update" 
			onClick= "if (! confirm('A role change may imply deletion of properties. Continue?')) return false;"/>
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
