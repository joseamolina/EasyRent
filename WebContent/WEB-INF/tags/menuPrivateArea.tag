<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<li>
	<a href="${pageContext.request.contextPath}/actor/update/${credentials.id_actor}.html">Edit my personal data</a>
</li>
<li>
	<a href="${pageContext.request.contextPath}/credentials/changePassword/${credentials.username}.html">Change password</a>
</li>
<li>
	<a href="${pageContext.request.contextPath}/credentials/delete/${credentials.username}.html"
		onClick= "if (! confirm('Are you sure you want to delete your account?')) return false;">Delete my account</a>
</li>
