<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-post.jspf"%>
<meta name="author" content="<s:property value="author" />">
<meta name="description" content="<s:property value="description" />" />
<meta name="og:url" content="http://www.ramblingware.com/author/<s:property value="uriName" />">
<meta name="og:type" content="website">
<meta name="og:title" content="<s:property value="name" />">
<meta name="og:image" content="<s:property value="thumbnail" />">
<meta name="og:description" content="<s:property value="description" />">

<meta name="twitter:card" content="summary">
<meta name="twitter:site" content="http://www.ramblingware.com/author/<s:property value="uriName" />">
<meta name="twitter:creator" content="<s:property value="author" />">
<meta name="twitter:title" content="<s:property value="name" />">
<meta name="twitter:description" content="<s:property value="description" />">
<meta name="twitter:image" content="<s:property value="thumbnail" />">
<!-- META_END -->
<title>About - <s:property value="author" /></title>
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
			
			<div id="page-content" class="w3-col m8 w3-container w3-padding">
							
				<h1><font class="footnote w3-right"><s:property value="createDate" /></font>
				<s:property value="author" /></h1>
				
				<div class="w3-container w3-padding">
				<p>
					<img src="<s:property value="thumbnail" />" class="w3-round w3-card-4 w3-margin-right w3-margin-bottom w3-left" style="max-height:200px" alt="Profile Picture" />
					
					<% out.print(request.getAttribute("htmlContent")); %>
				</p>
				</div>
					
				<br />
				<br />
				<div class="w3-container">
					
					<p class="footnote quote">Any opinions expressed here are solely my own, and do not express the views or opinions of any current or previous employer.</p>
					
				</div>
				<br />
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