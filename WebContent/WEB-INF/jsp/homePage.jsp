<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basicpage title="HomePage">
<jsp:body>
<h2>Welcome to Easy Rent!</h2>
<div id="myCarousel" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
  	<c:forEach items='${bestProperties}' var="property">
    <li data-target="#myCarousel" data-slide-to="${bestProperties.indexOf(property)}" 
    	${bestProperties.indexOf(property) == 0	? "class='active'":""}>
    </li>
    </c:forEach>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <c:forEach items='${bestProperties}' var="property">
	    <div class='item ${bestProperties.indexOf(property) == 0	? "active":""}'>
	    	<img src='${pageContext.request.contextPath}/img/${property.id}_0' alt="${property.title}">
	    		    	<div class="carousel-caption">
	          <a href="${pageContext.request.contextPath}/property/visualize/${property.id}.html">
				${property.title}</a>
        	</div>
	    </div>
	</c:forEach>
  </div>

  <!-- Left and right controls -->	
  <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>

</jsp:body>
</t:basicpage>
