<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-3">
		<form:form class= "form" method="post" modelAttribute="consult"
		action="${pageContext.request.contextPath}/property/listAll.html">
		<div class="form-group">
		<p>
			<form:label path="minDailyPrice">Min. daily Price:</form:label>
			<form:select path="minDailyPrice" class="form-control">
				<option value="5">5</option>
    			<option value="10">10</option>
    			<option value="25">25</option>
    			<option value="50">50</option>
    			<option value="100">100</option>
    			<option value="250">250</option>
    			<option value="500">500</option>
  			</form:select>
  			<form:errors path="minDailyPrice" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="maxDailyPrice">Max daily price:</form:label>
			<form:select path="maxDailyPrice" class="form-control">
    			<option value="5">5</option>
    			<option value="10">10</option>
    			<option value="25">25</option>
    			<option value="50">50</option>
    			<option value="100">100</option>
    			<option value="250">250</option>
    			<option value="500">500</option>
  			</form:select>
  			<form:errors path="maxDailyPrice" />
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="numberPeople">Number of people:</form:label>
			<form:select path="numberPeople" class="form-control">
    			<option value="1">1</option>
    			<option value="2">2</option>
    			<option value="3">3</option>
    			<option value="4">4</option>
    			<option value="5">5</option>
    			<option value="6">6</option>
    			<option value="7">7</option>
    			<option value="7">8</option>
  			</form:select>
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="rate">Rate:</form:label>
			<form:select path="rate" class="form-control">
    			<option label="&#9733" value="2.0"></option>
    			<option label="&#9733 &#9733" value="4.0"></option>
    			<option label="&#9733 &#9733 &#9733" value="6.0"></option>
    			<option label="&#9733 &#9733 &#9733 &#9733" value="8.0"></option>
    			<option label="&#9733 &#9733 &#9733 &#9733 &#9733" value="10.0"></option>
  			</form:select>
		</p>
		</div>
		<div class="form-group">
		<p>
			<form:label path="city">City:</form:label>
			<form:input path="city" class="form-control"/>
			<form:errors path="city" />
		</p>
		</div>
		<a href="${pageContext.request.contextPath}/property/listAll.html" class="btn btn-default">Clear</a>
		<input class="btn btn-default" type="submit" value="Search" />
	</form:form>
</div>