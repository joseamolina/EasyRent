<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Edit property periods">
<jsp:body>
	<t:menuEditProperty/>
	<div class="container">
		<div class="col-md-3">
			<h2>Property periods</h2>
			<c:if test='${periods.size() != 0}'>
			<table class="table">
				<tr>
					<th>Start date</th>
					<th>Finish date</th>
				</tr>
				<c:forEach items='${periods}' var="periodA">
				<tr>
					<td>${periodA.start_date}</td>
					<td>${periodA.finish_date}</td>
					<td><a href="${pageContext.request.contextPath}/period/delete/${periodA.start_date.hashCode()},${periodA.finish_date.hashCode()},${periodA.id_prop}.html" 
						class="btn btn-default">Delete</a>
				</tr>
				</c:forEach>
			</table>
			</c:if>
			<form:form class= "form" method="post" modelAttribute="period">
				<div class="form-group">
				<p>
					<form:label path="start_date">Start date (mm/dd/yyyy):</form:label>
					<form:input path="start_date" class="form-control"/>
					<form:errors path="start_date" />
				</p>
				</div>
				<div class="form-group">
				<p>
					<form:label path="finish_date">Finish date (mm/dd/yyyy):</form:label>
					<form:input path="finish_date" class="form-control"/>
					<form:errors path="finish_date" />
				</p>
				</div>
				<a class="btn btn-default" href="${pageContext.request.contextPath}/property/list.html" >Cancel</a>
				<input class="btn btn-default" type="submit" value="Add new period" />
			</form:form>
		</div>
	</div>
</jsp:body>
</t:basicpage>
