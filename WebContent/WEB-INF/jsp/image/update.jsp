<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Edit property images">
<jsp:body>
	<t:menuEditProperty/>
	<div class="container">
		<div class="col-md-3">
			<h2>Property images</h2>
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
					<td><a href="${pageContext.request.contextPath}/image/delete/${imageA.href.hashCode()},${imageA.id_prop}.html" class="btn btn-default">Delete</a>
				</tr>
				</c:forEach>
			</table>
			</c:if>
			<form:form class= "form" method="post" enctype="multipart/form-data"
				commandName="fileFormBean">
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
				<a class="btn btn-default" href="${pageContext.request.contextPath}/property/list.html" >Cancel</a>
				<input class="btn btn-default" type="submit" value="Upload">
			</form:form>
		</div>
	</div>
</jsp:body>
</t:basicpage>
