<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>

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
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
			
				<h1>Message</h1>
				<s:if test="message != null">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-blue w3-text-blue w3-border-blue" onclick="this.style.display='none'" data-close="">
						<span class="icon-info w3-large w3-margin-right"></span>${message}</p>
				</s:if>
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red" onclick="this.style.display='none'" data-close="">
						<span class="icon-cross w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-green w3-text-green w3-border-green" onclick="this.style.display='none'" data-close="">
						<span class="icon-checkmark w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>
				
				<p>
					<a class="w3-btn w3-card w3-round w3-light-grey" href="/"><span class="icon-home w3-large w3-margin-right"></span>Home Page</a>
				</p>
			
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