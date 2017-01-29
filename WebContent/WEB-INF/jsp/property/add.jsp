<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="Add property">
<jsp:body>
	<div class="progress">
	<div class="progress-bar" role="progressbar" aria-valuenow="25"
	aria-valuemin="0" aria-valuemax="100" style="width:25%">
	  Step 1: Property data
	</div>
	</div>
	<h2>Enter property data</h2>
	<form:form class= "form-horizontal" method="post" modelAttribute="property"
		action="${pageContext.request.contextPath}/property/add.html">
		<div class="col-md-3">
		<div class="form-group">
		<p>
			<form:label path="title">Title:</form:label>
			<form:input path="title" class="form-control"/>
			<form:errors path="title" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="description">Description:</form:label>
			<form:textarea rows="4" path="description" class="form-control"/>
			<form:errors path="description" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="property_type">Type:</form:label>
			<form:input path="property_type" class="form-control"/>
			<form:errors path="property_type" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="capacity">Capacity:</form:label>
			<form:input path="capacity" class="form-control"/>
			<form:errors path="capacity" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="num_rooms">Number of rooms:</form:label>
			<form:input path="num_rooms" class="form-control"/>
			<form:errors path="num_rooms" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="num_baths">Number of baths:</form:label>
			<form:input path="num_baths" class="form-control"/>
			<form:errors path="num_baths" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="num_beds">Number of beds:</form:label>
			<form:input path="num_beds" class="form-control"/>
			<form:errors path="num_beds" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="square_meters">Square meters:</form:label>
			<form:input path="square_meters" class="form-control"/>
			<form:errors path="square_meters" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="street">Street:</form:label>
			<form:input path="street" class="form-control"/>
			<form:errors path="street" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="number_home">Number:</form:label>
			<form:input path="number_home" class="form-control"/>
			<form:errors path="number_home" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="floor_home">Floor:</form:label>
			<form:input path="floor_home" class="form-control"/>
			<form:errors path="floor_home" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="city">City:</form:label>
			<form:input path="city" class="form-control"/>
			<form:errors path="city" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="daily_price">Daily price:</form:label>
			<form:input path="daily_price" class="form-control"/>
			<form:errors path="daily_price" />
		</p>
		</div>
		<a class="btn btn-default" href="${pageContext.request.contextPath}/property/list.html" >Cancel</a>
		<input class="btn btn-default" type="submit" value="Continue" />
		</div>
	</form:form>
</jsp:body>
</t:basicpage>
