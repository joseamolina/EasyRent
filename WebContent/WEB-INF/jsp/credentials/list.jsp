<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:basicpage title="Management of users">
<jsp:body>
	<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
	<h1>List of system users</h1>
	<table class="table">
		<tr>
			<th>Username</th>
			<th>Role</th>
			<th>Actor ID</th>
		</tr>
		<c:forEach items="${allCredentials}" var="credentialsA">
			<c:if test='${credentials != null && !credentials.username.equals(credentialsA.username)}'>
			<tr>
					<td>${credentialsA.username}</td>
					<td>${credentialsA.role}</td>
					<td>${credentialsA.id_actor}</td>
					<td><a href="update/${credentialsA.username}.html" class="btn btn-default">Edit</a>
					<td><a href="delete/${credentialsA.username}.html" class="btn btn-default"
						onClick= "if (! confirm('Are you sure you want to delete this user?')) return false;">Delete</a>
			</tr>
			</c:if>
		</c:forEach>
	</table>
	<a href="${pageContext.request.contextPath}/homePage.html" class="btn btn-default">Back</a>
	<a href="../actor/add.html" class="btn btn-default">Add user</a><br>
</jsp:body>
</t:basicpage>
