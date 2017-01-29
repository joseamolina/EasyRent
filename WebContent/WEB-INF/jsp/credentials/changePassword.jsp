<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Edit credentials">
<jsp:body>
	<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
	<h2>Change password</h2>
	<form:form class="form" method="post" modelAttribute="password">
	<div class="col-md-3">
		<div class="form-group">
		<p>
			<form:label path="current_password">Current password:</form:label>
			<form:password path="current_password" class="form-control"/>
			<form:errors path="current_password" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="new_password">New password:</form:label>
			<form:password path="new_password" class="form-control"/>
			<form:errors path="new_password" />
		</p>
		</div>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/homePage.html" >Cancel</a>
		<input type="submit" class="btn btn-default" value="Accept" />
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
