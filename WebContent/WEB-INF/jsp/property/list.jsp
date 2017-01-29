<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:basicpage title="List of properties">
<jsp:body>
	<h1>List of properties</h1>
	<table class="table">
		<tr>
			<th></th>
			<th>Name</th>
			<th>Daily price</th>
		</tr>
		<c:forEach items="${properties}" var="property">
			<tr>
				<td><a href="${pageContext.request.contextPath}/property/visualize/${property.id}.html">
					<img src ='${pageContext.request.contextPath}/img/${property.id}_0' height="90" width="120"/></a></td>
				<td><a href="${pageContext.request.contextPath}/property/visualize/${property.id}.html">${property.title}</a></td>
				<td>${property.daily_price}</td>
				<td><a href="update/${property.id}.html" class="btn btn-default">Edit</a>
				<td><a href="delete/${property.id}.html" class="btn btn-default"
					onClick= "if (! confirm('Are you sure you want to delete this property?')) return false;"
					>Delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<a href="${pageContext.request.contextPath}/homePage.html" class="btn btn-default">Back</a>
	<a href="../property/add.html" class="btn btn-default">Add property</a><br>
</jsp:body>
</t:basicpage>
