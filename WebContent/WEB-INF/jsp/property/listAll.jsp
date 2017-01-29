<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:basicpage title="List of properties">
<jsp:body>
	<t:menuSearchProperty/>
	<div class="container">
		<div class="col-md-8">
			<h1>List of properties</h1>
			<table class="table">
				<tr>
					<th></th>
					<th>Name</th>
					<th>Daily price</th>
					<th>Rate</th>
				</tr>
				<c:forEach items="${properties}" var="property">
					<tr>
							<td><a href="${pageContext.request.contextPath}/property/visualize/${property.id}.html">
								<img src ='${pageContext.request.contextPath}/img/${property.id}_0' height="90" width="120"/></a></td>
							<td><a href="${pageContext.request.contextPath}/property/visualize/${property.id}.html">${property.title}</a></td>
							<td>${property.daily_price}</td>
							<td>
								<p>
									<c:forEach var="i" begin="0" end='${property.getRateOverFive()-1}'>
										<span class="glyphicon glyphicon-star" aria-hidden="true"></span>
									</c:forEach>
								</p>
							</td>
					</tr>
				</c:forEach>
			</table>
			<a href="${pageContext.request.contextPath}/homePage.html" class="btn btn-default">Back</a>	
		</div>
	</div>
</jsp:body>
</t:basicpage>
