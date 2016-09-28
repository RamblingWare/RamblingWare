<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %><%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<title>404 File Not Found - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta.jspf"%>
<!-- META_END -->
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
			
			<div id="page-content" class="w3-col m8 w3-container w3-border w3-padding w3-card-2">
				
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>Error: 404</h1>
				<p>File Not Found. Looks like the page you've requested is no longer available, or was moved.</p>
				
				<div class="w3-center">
					<img class="w3-round w3-border w3-opacity" alt="Sad Robot" title="Sad Robot" src="//i.imgur.com/pHKz09Fm.png">
				</div>
				
				<br />
				<p>You can try searching for the page :</p>
				
				<div class="w3-center">
					<form action="/search" method="get">
					<input id="s" name="s" class="icon-search w3-input w3-theme-light w3-card-2 w3-round-large" maxlength="50" size="20" placeholder="Search..." type="text">
					</form>
				</div>
				
				<br />
				<br />
				
				
				<%! int hits = 1; %>
				<!-- Error JSP Hits: <%=hits++ %>  -->
			
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>