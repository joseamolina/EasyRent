<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- The session is automatically available in the object "session" -->
<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
<c:choose>
<c:when test='${credentials == null}'>
<li>
	<a href="${pageContext.request.contextPath}/login.html">Login
		<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span></a>
</li>
</c:when>
<c:otherwise>
<li>
	<a href="${pageContext.request.contextPath}/logout.html">Logged as ${credentials.username}, Exit
		 <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span></a>
</li>
</c:otherwise>
</c:choose>
