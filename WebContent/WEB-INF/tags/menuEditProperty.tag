<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<div class="col-md-3">
	<ul class="nav nav-pills nav-stacked">
	  <li><a href="${pageContext.request.contextPath}/property/update/${property.id}.html">Data</a></li>
	  <li><a href="${pageContext.request.contextPath}/image/update/${property.id}.html">Images</a></li>
	  <li><a href="${pageContext.request.contextPath}/service/update/${property.id}.html">Services</a></li>
	  <li><a href="${pageContext.request.contextPath}/period/update/${property.id}.html">Periods</a></li>
	</ul>
</div>