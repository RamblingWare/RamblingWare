<!DOCTYPE HTML>
<html>
<head>
<#include "/WEB-INF/templates/fragment/meta/meta.ftl">

<title>Blog - ${(name)!"Oddox"}</title>
</head>
<body class="w3-theme-dark">

	<#include "/WEB-INF/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/WEB-INF/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>All Posts</h1>
				
				<s:if test="posts != null">
				<s:if test="posts.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						Something went wrong because no posts were found. Please try again later?</p>
				</s:if>
				<s:else>
					<div class="w3-row">
					<s:iterator value="posts" status="r">
						<div class="w3-col sm12 m6 l4 w3-padding"><#include "/WEB-INF/templates/blog/block-post.ftl"></div>
					</s:iterator>
					</div>		
					<#include "/WEB-INF/templates/fragment/pagination.ftl">		
				</s:else>
				</s:if>
			</div>
			
			<#include "/WEB-INF/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/WEB-INF/templates/fragment/footer.ftl">
</body>
</html>