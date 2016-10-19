<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta.jspf"%>
<!-- META_END -->
<title>RamblingWare</title>
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
			
			<div id="page-content" class="w3-col m8 w3-container w3-padding">
				
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
						<p class="error"><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
						<p class="info"><s:property/></p>
					</s:iterator>
				</s:if>
				
				<h1>Welcome</h1>
				<p>This is my blog about computers, programming, tech, and things that bother me. I hope it bothers you too.</p>
				
				
				<!-- BLOG POSTS START -->
				<br />
				<br />
				<h2>Blog Posts</h2>
				<s:if test="results != null">
				<s:if test="results.isEmpty()">
					<p class="footnote">Something went wrong because no results were found. Please try again later?</p>
				</s:if>
				<s:else>
					<div class="w3-row" style="min-height:0px">
					<s:iterator value="results" status="r">
					
					<s:if test="#r.index < 3">
						<div class="w3-col s6 m4 l4 w3-padding-left w3-padding-right">
						<div class="w3-display-container w3-margin-0 w3-padding-0 w3-round w3-hover-shadow w3-card w3-center" style="min-height: 150px; max-height: 150px; overflow: hidden;">
							<a href="/blog/post/<s:property value="uriName" />">
								<img alt="Thumbnail" class="w3-margin-0 w3-round w3-padding-0" style="width:100%;" src="<s:property value="thumbnail" />">
								<span class="w3-theme-light w3-padding-4 bottomright" style="width: 100%;"><s:property value="title" /></span>
							</a>
						</div>
						</div>
					</s:if>
					
					</s:iterator>
					
					<div class="w3-col s6 m12 l12 w3-padding-left w3-padding-right w3-center">
						<p><a class="w3-btn w3-border w3-theme-light w3-round" href="/blog" style="white-space: pre-wrap;">See more blog posts...</a></p>
					</div>
					
					</div>					
				</s:else>
				</s:if>
				<!-- BLOG POSTS END -->
				
				<!-- AUTHORS START -->
				<br />
				<br />
				<h2>Author</h2>
				<div class="w3-row" style="min-height:0px">
				<div class="w3-col s12 m10 l7 w3-padding-0 w3-margin-0 w3-round w3-hover-shadow w3-card">
					<a href="/about">
					<div class="w3-col s3 m3 l3 w3-padding-16">
						<img class="w3-round w3-margin-left" style="width: 40px; height: 40px" alt="Profile Picture" src="/img/author/AustinDelamar-small.jpg">
					</div>
					<div class="w3-col s9 m9 l9 w3-padding-16">
						<p class="footnote w3-padding-right">
						<b>Austin Delamar</b><br />
						<span class="w3-small">
						Java Developer, Burger &amp; Beer enthusiast, and Owner of RamblingWare.
						When Austin's not at his computer, he likes to relax outdoors, play tennis,
						snow ski, play guitar, and talk about himself in third-person.</span> 
						</p>
					</div>
					</a>
				</div>
								
				</div>
				<!-- AUTHORS END -->
				
				<br />
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
	<!-- CONTENT END -->

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>