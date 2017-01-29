<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Edit actor">
<jsp:body>
	<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
	<h2>Personal data</h2>
	<form:form class="form" method="post" modelAttribute="actor">
	<div class="col-md-3">
		<c:choose>
		<c:when test='${credentials.role == "tenant"}'>
		<p>You are a tenant</p>
		</c:when>
		<c:otherwise>
		<p>You are an ${credentials.role}</p>
		</c:otherwise>
		</c:choose>
		<div class="form-group">
		<p>
			<form:label path="id">ID:</form:label>
			<form:input path="id" readonly="true" class="form-control"/>
			<form:errors path="id" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="name">Name:</form:label>
			<form:input path="name" class="form-control"/>
			<form:errors path="name" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="surname">Surname:</form:label>
			<form:input path="surname" class="form-control"/>
			<form:errors path="surname" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="email">Email:</form:label>
			<form:input path="email" class="form-control"/>
			<form:errors path="email" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="postcode">CP:</form:label>
			<form:input path="postcode" class="form-control"/>
			<form:errors path="postcode" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="phone_number">Phone number:</form:label>
			<form:input path="phone_number" class="form-control"/>
			<form:errors path="phone_number" />
		</p>
		</div>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/homePage.html" >Cancel</a>
		<input type="submit" class="btn btn-default" value="Update" />
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
