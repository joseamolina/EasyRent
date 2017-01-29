<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:basicpage title="List of reservations">
<jsp:body>
	<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
	<h1>Reservations</h1>
	<table class="table">
		<tr>
			<th>Property Id</th>
			<th>Tenant Id</th>
			<th>Start date</th>
			<th>Finish date</th>
			<th>Num. people</th>
			<th>Tot. amount</th>
		</tr>
		<c:forEach items="${reservations}" var="reservation">
			<tr>
				<td><a href="${pageContext.request.contextPath}/property/visualize/${reservation.id_property}.html">${reservation.id_property}</a></td>
				<td>${reservation.tenant}</td>
				<td>${reservation.start_date}</td>
				<td>${reservation.finish_date}</td>
				<td>${reservation.num_people}</td>
				<td>${reservation.total_amount}</td>
				<c:choose>
				<c:when test='${credentials.role == "owner" && reservation.status == "pending"}'>
					<td><a href="${pageContext.request.contextPath}/reservation/accept/${reservation.tracking_number}.html" class="btn btn-default">Accept</a>
					<td><a href="${pageContext.request.contextPath}/reservation/reject/${reservation.tracking_number}.html" class="btn btn-default">Reject</a>
				</c:when>
				<c:otherwise>
				<c:choose>
				<c:when test='${reservation.status == "accepted"}'>
				<td><a href="${pageContext.request.contextPath}/pdf/invoice_${reservation.tracking_number}.pdf" 
					target="_blank" class="btn btn-default">View invoice</a>
				</c:when>
				<c:otherwise>
				<td>${reservation.status}</td>
				</c:otherwise>
				</c:choose>
				</c:otherwise>
			</c:choose>
			</tr>
			
		</c:forEach>
	</table>
	<a href="${pageContext.request.contextPath}/homePage.html" class="btn btn-default">Back</a>
</jsp:body>
</t:basicpage>
