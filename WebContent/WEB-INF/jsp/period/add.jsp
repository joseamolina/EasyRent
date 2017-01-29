<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Add property periods">
<jsp:body>
	<div class="progress">
	<div class="progress-bar" role="progressbar" aria-valuenow="100"
	aria-valuemin="0" aria-valuemax="100" style="width:100%">
	Step 4: Property periods
	</div>
	</div>
	<h2>Add available periods</h2>
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
			<td><a href="cancel/${periodA.start_date.hashCode()},${periodA.finish_date.hashCode()}.html" class="btn btn-default">Delete</a>
		</tr>
		</c:forEach>
	</table>
	</c:if>
	<form:form class= "form" method="post" modelAttribute="period"
		action="${pageContext.request.contextPath}/period/add.html">
		<div class="col-md-3">
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
		<a class="btn btn-default" href="${pageContext.request.contextPath}/service/add.html" >Back</a>
		<input class="btn btn-default" type="submit" value="Add period" />
		<a class="btn btn-default" href="${pageContext.request.contextPath}/property/addFinally.html" >Finish</a><br>
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
