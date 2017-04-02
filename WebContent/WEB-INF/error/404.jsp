<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %><%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>

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
				
				<div class="w3-padding-0 w3-animate-opacity w3-margin-0">
					<img class="w3-img w3-round w3-card-4" style="width: 100%;" alt="Photo for WTF?!" title="Sad Robot" src="https://i.imgur.com/pHKz09F.png">
					<p class="footnote w3-tiny w3-right">Photo Credit: bamenny</p>
				</div>
				
				<h1>404: File Not Found</h1>
				
				<p>
					Looks like the page you've requested is no longer available, or was moved.
				</p>
				
				<br />
				
				<div class="w3-center">
					<p>Maybe try searching for it?</p>
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