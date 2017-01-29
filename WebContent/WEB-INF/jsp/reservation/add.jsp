<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Booking">
<jsp:body>
	<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
	
	<c:choose>
	<c:when test='${credentials == null}'>
		<h2>You are not Logged in. You should either log in or register in case you are not.</h2>
	</c:when>
	<c:otherwise>
	<h2>${credentials.username}, you are going to make a reservation:</h2>
	</c:otherwise>
	</c:choose>
	<h3>Reservation info</h3>
	<table class="table">
		<tr>
			<th>Entry date</th>
			<td>${reservation.start_date}</td>
		</tr>
		<tr>
			<th>Exit date</th>
			<td>${reservation.start_date}</td>
		</tr>
		<tr>
			<th>Total amount</th>
			<td>$ ${reservation.total_amount}</td>
		</tr>
		<tr>
			<th>Property</th>
			<td><a href="${pageContext.request.contextPath}/property/visualize/${reservation.id_property}.html" target="_blank">
				<img src ='${pageContext.request.contextPath}/img/${reservation.id_property}_0' height="90" width="120"/></a></td>
		</tr>
	</table>
	<div class="col-md-3">
		<form:form class="form" method="post" modelAttribute="reservation">
			<div class="form-group">
				<form:label path="num_people">Select the number of people: </form:label>
				<form:select path = "num_people" class="form-control">
					<c:forEach var="i" begin="1" end="${reservation.num_people}">
						<option value="${i}">${i}</option>
					</c:forEach>
				</form:select>
			</div>
			<a class="btn btn-default" href="${pageContext.request.contextPath}/property/visualize/${reservation.id_property}.html" >Cancel</a>
			<input class="btn btn-default" type="submit" value="Accept" />
		</form:form>
	</div>
</jsp:body>
</t:basicpage>
