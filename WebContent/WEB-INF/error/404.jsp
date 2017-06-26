<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	isErrorPage="true"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>

<title>404 File Not Found - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<div class="w3-padding-0 w3-animate-opacity w3-margin-0">
					<img class="w3-img w3-round w3-card-4" style="width: 100%;" alt="404 Robot" title="404 Robot" src="/img/robot.png" />
					<p class="w3-tiny w3-text-grey w3-margin-0 w3-right">404 Love Not Found. (Photo Credit: bamenny)</p>
				</div>
				
				<h1>404: File Not Found</h1>
				
				<p>
					Looks like the page you've requested is no longer available, or was moved.
				</p>
				
				<br />
				
				<div class="w3-center">
					<p>Maybe try searching for it?</p>
					<form action="/search" method="get">
					<label for="s" class="icon-search w3-large w3-text-theme"></label>
					<input id="s" name="s" class="w3-input w3-hover-shadow w3-card w3-round-large" style="display:inline; width:75%" maxlength="50" size="20" placeholder="Search..." type="text">
					</form>
				</div>
			
			</div>
			
			<%@include file="/WEB-INF/fragment/archive.jspf" %>			
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>	
</body>
</html>