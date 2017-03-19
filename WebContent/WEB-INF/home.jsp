<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<%@include file="/WEB-INF/fragment/meta.jspf"%>

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
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Welcome!</h1>
				<p>
					This is my blog about computers, programming, tech, and things that bother me. I hope it bothers you too.
					<a href="/about/">Read more...</a>
				</p>
				
				<!-- BLOG POSTS START -->
				<br />
				<h2>Blog Posts</h2>
				<s:if test="posts != null">
				<s:if test="posts.isEmpty()">
					<p class="footnote">Something went wrong because no posts were found. Please try again later?</p>
				</s:if>
				<s:else>
					<div class="w3-row" style="min-height:0px">
					<s:iterator value="posts" status="r">
					
						<div class="w3-container w3-round-large w3-border w3-card w3-hover-shadow w3-padding-0">
						
						<div class="w3-container w3-round-large w3-col s12 m3 l4 w3-padding-0 w3-center w3-theme-l4" style="overflow: hidden;">
							<a href="/blog/post/<s:property value="uriName" />">
							<img class="thumbnail" src="<s:property value="thumbnail" />" alt="Photo for <s:property value="title" />" title="<s:property value="bannerCaption" />" />
							</a>
						</div>
						
						<div class="w3-container w3-round w3-col s12 m9 l8 w3-padding-16">
						<h3 class="w3-padding-0 w3-margin-0"><a href="/blog/post/<s:property value="uriName" />"><s:property value="title" /></a></h3>
						<p class="footnote"><s:property value="description" /><br/><br/></p>
						
						<p class="footnote">Tags:
						<s:if test="tags != null && !tags.isEmpty()">
							<s:iterator value="tags">
								&nbsp;<a class="tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" href="/tag/<s:property />"><s:property /></a>
							</s:iterator>
						</s:if>
						
						<p class="footnote"><s:property value="author.name" />&nbsp;|&nbsp;<s:property value="publishDateReadable" />
						&nbsp;|&nbsp;<a class="footnote" href="/blog/post/<s:property value="uriName" />#comments"><span class="disqus-comment-count" data-disqus-identifier="<s:property value="uriName" />"></span></a></p>
						
						</div>
						</div><br />
					</s:iterator>
					
					<div class="w3-container w3-padding-left w3-padding-right w3-center">
						<p class="w3-large"><a href="/blog">See more blog posts...</a></p>
					</div>
					
					</div>
				</s:else>
				</s:if>
				<!-- BLOG POSTS END -->
				
				<!-- AUTHORS START -->
				<br />
				<h2>About the Author</h2>
				
				<s:if test="authors != null">
				<s:if test="authors.isEmpty()">
					<p class="footnote">Something went wrong because no results were found. Please try again later?</p>
				</s:if>
				<s:else>
					<div class="w3-row" style="min-height:0px">
					<s:iterator value="authors" status="r">
					
					<s:if test="#r.index < 3">
						<div class="w3-col s12 m10 l7 w3-padding-0 w3-margin-0 w3-round w3-hover-shadow w3-card">
							<a href="/author/<s:property value="uriName" />">
							<span class="w3-col s3 m3 l3 w3-padding-16">
								<img class="w3-round w3-margin-left" style="width: 75%;" alt="Profile Picture" src="<s:property value="thumbnail" />">
							</span>
							<span class="w3-col s9 m9 l9 w3-padding-16">
								<p class="footnote w3-padding-right">
								<b><s:property value="Name" /></b><br />
								<span class="w3-small"><s:property value="description" /></span> 
								</p>
							</span>
							</a>						
						</div>
					</s:if>
					
					</s:iterator>					
					
					</div>					
				</s:else>
				</s:if>
				<!-- AUTHORS END -->
				
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