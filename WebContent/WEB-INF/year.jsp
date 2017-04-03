<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
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
				
				<h1>Year: <s:property value="year" /></h1>
				
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
					
						<div class="w3-container w3-round w3-border w3-card w3-hover-shadow w3-padding-0">
						
						<div class="w3-container w3-round w3-col s12 m3 l4 w3-padding-0 w3-center w3-theme-l4" style="overflow: hidden;">
							<a href="/blog/<s:property value="uriName" />">
							<img class="thumbnail" src="<s:property value="thumbnail" />" alt="Photo for <s:property value="title" />" title="<s:property value="bannerCaption" />" />
							</a>
						</div>
						
						<div class="w3-container w3-round w3-col s12 m9 l8 w3-padding-16">
						<h3 class="w3-padding-0 w3-margin-0"><a href="/blog/<s:property value="uriName" />"><s:property value="title" /></a></h3>
						<p class="footnote"><s:property value="description" /><br/><br/></p>
						
						<p class="footnote">Tags:
						<s:if test="tags != null && !tags.isEmpty()">
							<s:iterator value="tags">
								&nbsp;<a class="tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" href="/tag/<s:property />"><s:property /></a>
							</s:iterator>
						</s:if>
						</p>
						<p class="footnote"><s:property value="author.name" />&nbsp;|&nbsp;<s:property value="publishDateReadable" />
						&nbsp;|&nbsp;<a class="footnote" href="/blog/<s:property value="uriName" />#comments"><span class="disqus-comment-count" data-disqus-identifier="<s:property value="uriName" />"></span></a></p>
						</div>
						</div><br />
					</s:iterator>
				
				</s:else>
				</s:if>
				<!-- POSTS END -->
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