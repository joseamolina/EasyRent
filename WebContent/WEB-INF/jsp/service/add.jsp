<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Add property services">
<jsp:body>
	<div class="progress">
	<div class="progress-bar" role="progressbar" aria-valuenow="75"
	aria-valuemin="0" aria-valuemax="100" style="width:75%">
	Step 3: Property services
	</div>
	</div>
	<h2>Add property services</h2>
	<c:if test='${services.size() != 0}'>
	<table class="table">
		<tr>
			<th>Name</th>
		</tr>
		<c:forEach items='${services}' var="serviceA">
		<tr>
			<td>${serviceA.name}</td>
			<td><a href="cancel/${serviceA.name.hashCode()}.html" class="btn btn-default">Delete</a>
		</tr>
		</c:forEach>
	</table>
	</c:if>
	<form:form class= "form" method="post" modelAttribute="service"
		action="${pageContext.request.contextPath}/service/add.html">
		<div class="col-md-3">
		<div class="form-group">
		<p>
			<form:label path="name">Name:</form:label>
			<form:input path="name" class="form-control"/>
			<form:errors path="name" />
		</p>
		</div>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/image/add.html" >Back</a>
		<input class="btn btn-default" type="submit" value="Add service" />
		<a class="btn btn-default" href="${pageContext.request.contextPath}/period/add.html" >Continue</a><br>
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
