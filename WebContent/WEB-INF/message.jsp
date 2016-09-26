<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta.jspf"%>
<!-- META_END -->
<title>Message - RamblingWare</title>
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
			
				<h1>Message</h1>
				<p class="info"><%=request.getAttribute("message") %></p>
				<s:if test="hasActionMessages()">
		    		<div id="this" class="success" style="display:block"><s:iterator value="actionMessages"><s:property escape="false" /></s:iterator>
		    		<button style="float:right" onclick="toggleElement('this')">X</button></div>
				</s:if>
				<p>Use the menu or these links to continue using this website.
				<br />
				<br /><a href="/">Home page</a>
				<br /><a href="/blog">Blog page</a>
				<br /><a href="/blog/search">search page</a>
				</p>
				<br />
				
				<%! int hits = 1; %>
				<!-- Message JSP Hits: <%=hits++ %>  -->
			
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>