<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Edit property services">
<jsp:body>
	<t:menuEditProperty/>
	<div class="container">
		<div class="col-md-3">
			<h2>Property services</h2>
			<c:if test='${services.size() != 0}'>
			<table class="table">
				<tr>
					<th>Name</th>
				</tr>
				<c:forEach items='${services}' var="serviceA">
				<tr>
					<td>${serviceA.name}</td>
					<td><a href="${pageContext.request.contextPath}/service/delete/${serviceA.name},${serviceA.id_prop}.html" class="btn btn-default">Delete</a>
				</tr>
				</c:forEach>
			</table>
			</c:if>
			<form:form class= "form" method="post" modelAttribute="service">
				<div class="form-group">
				<p>
					<form:label path="name">Name:</form:label>
					<form:input path="name" class="form-control"/>
					<form:errors path="name" />
				</p>
				</div>
				<a class="btn btn-default" href="${pageContext.request.contextPath}/property/list.html" >Cancel</a>
				<input class="btn btn-default" type="submit" value="Add new service" />
			</form:form>
		</div>
	</div>
</jsp:body>
</t:basicpage>
