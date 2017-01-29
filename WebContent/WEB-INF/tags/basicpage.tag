<%@ tag description="Structure of a basic page"
	pageEncoding="UTF-8"%>
<%@ attribute name="title" required="false"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${title}</title>
	
	<!-- Custom -->
	<link href="${pageContext.request.contextPath}/css/caption.css"
		rel="stylesheet">
	
	<!-- Bootstrap -->
	<link href="${pageContext.request.contextPath}/css/bootstrap.css"
		rel="stylesheet">
		
	<!-- Custom styles -->
	<link href="${pageContext.request.contextPath}/css/carousel.css"
		rel="stylesheet">
	
	<!-- Scripts -->
	<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>	
	<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>	
	
</head>
<body>
	<header class="container page-header">

	</header>
		<t:navigation />
		<div class="container">
		<jsp:doBody />
		</div>
	<footer class="container panel-footer">
	<hr>
	<p class="text-muted">
	EI1027 - Design and implementation of Information Systems 
	</p>
	</footer>
</body>
</html>