<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Add property images">
<jsp:body>
	<div class="progress">
	<div class="progress-bar" role="progressbar" aria-valuenow="50"
	aria-valuemin="0" aria-valuemax="100" style="width:50%">
	Step 2: Property images
	</div>
	</div>
	<h2>Add property images</h2>
	<c:if test='${images.size() != 0}'>
	<table class="table">
		<tr>
			<th>Image</th>
			<th>Caption</th>
		</tr>
		<c:forEach items='${images}' var="imageA">
		<tr>
			<td><img src ='${pageContext.request.contextPath}/${imageA.href}' height="90" width="120"/></td>
			<td>${imageA.caption}</td>
			<td><a href="cancel/${imageA.href.hashCode()}.html" class="btn btn-default">Delete</a>
		</tr>
		</c:forEach>
	</table>
	</c:if>
	<form:form class= "form" method="post" enctype="multipart/form-data"
		commandName="fileFormBean">
	<div class="col-md-3">
		<div class="form-group">
			<form:label path="file">File:</form:label>
			<input class="btn btn-default" type="file" accept="image/*" name="file">
			<form:errors path = "file"/>
		</div>
		<div class="form-group">
		<p>
			<form:label path="caption">Caption:</form:label>
			<form:input path="caption" class="form-control"/>
		</p>
		</div>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/property/add.html" >Back</a>
		<input class="btn btn-default" type="submit" value="Upload">
		<a class="btn btn-default" href="${pageContext.request.contextPath}/service/add.html" >Continue</a><br>
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
