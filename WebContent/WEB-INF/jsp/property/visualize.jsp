<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
<t:basicpage title="Visualize property">
<jsp:body>
	<c:set var="vote" scope="request" value='${session.getAttribute("vote"+property.id)}'/>
	<div class="row">
		<div class="col-md-6">
			<h1>${property.title}</h1>
				<p>
					<c:forEach var="i" begin="0" end='${property.getRateOverFive()-1}'>
						<span class="glyphicon glyphicon-star" aria-hidden="true"></span>
					</c:forEach>
				</p>
			<p>
			${property.description}
			</p>
			<table class="table">
				<tr>
					<td>Type: </td>
					<td>${property.property_type}</td>
				</tr>
				<tr>
					<td>Capacity: </td>
					<td>${property.capacity}</td>
				</tr>
				<tr>
					<td>Num. rooms: </td>
					<td>${property.num_rooms}</td>
				</tr>
				<tr>
					<td>Num. baths: </td>
					<td>${property.num_baths}</td>
				</tr>
				<tr>
					<td>Num.beds: </td>
					<td>${property.num_beds}</td>
				</tr>
				<tr>
					<td>Square meters: </td>
					<td>${property.square_meters}</td>
				</tr>
				<tr>
					<td>Address: </td>
					<td>${property.getAddress()}</td>
				</tr>
				<tr>
					<td>Daily price: </td>
					<td>${property.daily_price}</td>
				</tr>
			</table>
			<h2>Services</h2>
			<c:if test='${services.size() != 0}'>
				<c:forEach items='${services}' var="service">
				<p>${service.name}</p>
				</c:forEach>
			</c:if>
		</div>
		<div class="col-md-4">
			<c:if test='${images.size() != 0}'>
			<table class="table">
				<c:forEach items='${images}' var="image">
					<div style="width:400px; padding:10px; margin:0 auto;">
						<div class='caption' title='${image.caption}'>
							<a href = '${pageContext.request.contextPath}/${image.href}' target="_blank">
							<img src='${pageContext.request.contextPath}/${image.href}'/>
							</a>
						</div>
					</div>
				</c:forEach>
			</table>
		</c:if>
		</div>
	</div>
	
	
	<h2>Available periods</h2>
	<c:if test='${periods.size() != 0}'>
	<table class="table">
		<tr>
			<th>Start date</th>
			<th>Finish date</th>
		</tr>
		<c:forEach items='${periods}' var="period">
		<tr>
			<td>${period.start_date}</td>
			<td>${period.finish_date}</td>
			<c:if test='${credentials == null || credentials.role == "tenant"}'>
			<td>
				<a href="${pageContext.request.contextPath}/reservation/add/${period.id_prop},${period.start_date.hashCode()},${period.finish_date.hashCode()}.html" class="btn btn-default">Reserve</a>
			</td>
			</c:if>
		</tr>
		</c:forEach>
	</table>
	</c:if>
	
	<c:if test='${vote == null && credentials != null && credentials.role == "tenant"}'>
		<div class="col-md-3">
			<form class ="form" method="post" action="${pageContext.request.contextPath}/property/vote/${property.id}.html" >
				<label>Vote this property!</label>
				<select name="vote" class="form-control">
					<c:forEach var="i" begin="0" end="10">
						<option value="${i}">${i}</option>
					</c:forEach>
				</select>
				<input type="submit" value="Vote!" class="form-control" />
			</form>
		</div>
	</c:if>
</jsp:body>
</t:basicpage>