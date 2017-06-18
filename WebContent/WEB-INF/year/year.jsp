<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta.jspf"%>

<title>Year <s:property value="year" /> - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->

	<!-- CONTENT BEGIN -->
	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-time w3-text-theme"></span>&nbsp;Year: <s:property value="year" /></h1>
				
				<!-- POSTS START -->
				<s:if test="posts != null">
				<s:if test="posts.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No results were found for that year.</p>
				</s:if>
				<s:else>
					<s:if test="posts.size() == 1">	
						<p>1 blog post published in <s:property value="year" />.<br /></p>
					</s:if>
					<s:else>
						<p><s:property value="posts.size()" /> blog posts published in <s:property value="year" />.<br /></p>
					</s:else>
					
					<s:iterator value="posts" status="r">
						<%@include file="/WEB-INF/blog/card-post.jspf" %>
					</s:iterator>
					
					<%@include file="/WEB-INF/fragment/pagination.jspf" %>
				
				</s:else>
				</s:if>
				<!-- POSTS END -->
				
				<div class="w3-container w3-padding-left w3-padding-right w3-center">
					<hr />
					<p class="w3-large"><a href="/year/">See more years...</a></p>
				</div>
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
		</div>
	</article>
	<!-- CONTENT END -->

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>