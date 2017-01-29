<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<c:set var="credentials" scope="request" value='${session.getAttribute("credentials")}'/>
<c:set var="numPendingReservations" scope="request" value='${session.getAttribute("numPendingReservations")}'/>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-collapse collapse">
			<div class="navbar-header">
      			<a class="navbar-brand" href="${pageContext.request.contextPath}/homePage.html">Easy Rent</a>
    		</div>
			<ul class="nav navbar-nav">
				<li>
					<a href="${pageContext.request.contextPath}/homePage.html">Home</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/property/listAll.html">List of properties</a>
				</li>
				<c:if test='${credentials != null}'>
				<c:if test='${credentials.role == "tenant"}'>
				<li>
					<a href="${pageContext.request.contextPath}/reservation/list.html">My reservations</a>
				</li>
				</c:if>
				<c:if test='${credentials.role == "owner"}'>
				<li>
					<a href="${pageContext.request.contextPath}/property/list.html">My properties</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/reservation/list.html">Reservations
					<c:if test='${numPendingReservations != null && numPendingReservations != 0}'>
						<span class="glyphicon glyphicon-bell" style="color:orange" aria-hidden="true"></span>
						${numPendingReservations}
					</c:if>
					</a>
				</li>
				</c:if>
				<c:if test='${credentials.role == "administrator"}'>
				<li>
					<a href="${pageContext.request.contextPath}/credentials/list.html">Management of users</a>
				</li>
				</c:if>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">Private area
			        <span class="caret"></span></a>
			        <ul class="dropdown-menu">
						<t:menuPrivateArea />
			        </ul>	
				</li>
				</c:if>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<t:logininfo />
    		</ul>
		</div>
	</div>
</nav>