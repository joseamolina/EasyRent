<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:basicpage title="${alert.page_title}">
<jsp:body>
	<h1>${alert.page_title}</h1>
	<div class="col-md-6">
		<p>${alert.description}</p>
		<a class="btn btn-default" href="${pageContext.request.contextPath}${alert.next_url}" >${alert.button_label}</a>
	</div>
</jsp:body>
</t:basicpage>