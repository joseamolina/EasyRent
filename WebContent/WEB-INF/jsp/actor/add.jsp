<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Registration">
<jsp:body>
	<div class="progress">
	<div class="progress-bar" role="progressbar" aria-valuenow="50"
	aria-valuemin="0" aria-valuemax="100" style="width:50%">
	  Step 1: Personal data
	</div>
	</div>
	<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
	<h2>Enter personal data</h2>
	<form:form class= "form" method="post" modelAttribute="actor"
		action="${pageContext.request.contextPath}/actor/add.html">
		<div class="col-md-3">
		<div class="form-group">
		<p>
			<form:label path="id">ID:</form:label>
			<form:input path="id" class="form-control"/>
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
		<c:choose>
		<c:when test='${credentials != null && credentials.getRole().equals("administrator")}'>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/credentials/list.html" >Cancel</a>
		</c:when>
		<c:otherwise>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/login.html" >Cancel</a>
		</c:otherwise>
		</c:choose>
		<input class="btn btn-default" type="submit" value="Continue" />
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
