<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %><%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Error - RamblingWare</title>
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
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>Error</h1>
				<p>Oops! Looks like Something broke.</p>
				<br />
				<p class="footnote"><% if(exception!=null)exception.printStackTrace(new java.io.PrintWriter(out)); %></p>
				<% if(exception!=null)System.err.println("Exception: "+exception.getClass().getName()+" "+exception.getMessage()); %>
				<p>Use the menu or these links to continue using this website.
				<br />
				<br /><a href="/">Home page</a>
				<br /><a href="/blog">Blog page</a>
				<br /><a href="/blog/search">search page</a>
				</p>
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