<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %><%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta.jspf"%>

<title>404 File Not Found - RamblingWare</title>
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
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Error: 404</h1>
				
				<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
					File Not Found. Looks like the page you've requested is no longer available, or was moved.
				</p>
				
				<div class="w3-center">
					<img class="w3-round w3-border w3-opacity" alt="Sad Robot" title="Sad Robot" src="//i.imgur.com/pHKz09Fm.png">
				</div>
				
				<br />
				<p>Try searching:</p>
				
				<div class="w3-center">
					<form action="/search" method="get">
					<label for="s" class="icon-search w3-xlarge w3-text-theme"></label>
					<input id="s" name="s" class="w3-input w3-hover-shadow w3-card w3-round-large" style="display:inline; width:75%" maxlength="50" size="20" placeholder="Search..." type="text">
					</form>
				</div>
			
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
			<!-- RECENTLY VIEWED BEGIN -->
			<%@include file="/WEB-INF/fragment/recentlyviewed.jspf" %>
			<!-- RECENTLY VIEWED END -->
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>