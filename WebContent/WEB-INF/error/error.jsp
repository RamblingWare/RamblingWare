<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	isErrorPage="true"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Error - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Error</h1>
				<p>Oops! Looks like Something broke.</p>
				
				<% if(exception!=null) { %>
				<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
				<span class="icon-cross w3-large w3-margin-right"></span>	
				<% exception.printStackTrace(new java.io.PrintWriter(out)); %>
				</p>	
				<% } %>
				
				<p class="w3-small w3-text-grey"><% if(exception!=null)System.err.println("Exception: "+exception.getClass().getName()+" "+exception.getMessage()); %></p>
				
				<br />
				
				<p>
					<a class="w3-btn w3-card w3-round w3-light-grey" href="javascript: window.history.back()">
						<span class="icon-arrow-left w3-large"></span>&nbsp;&nbsp;Go Back</a>
				</p>
			
			</div>
		</div>
	</article>

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>