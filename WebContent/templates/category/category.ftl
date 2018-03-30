<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Category ${(category)!''} - ${(name)!'Oddox'}</title>
</head>
<body>

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">
				
				<h1 style="vertical-align: middle;"><span class="icon-folder"></span>&nbsp;Category: ${(category)!''}</h1>

				<#if posts??>
					<#if posts.size() == 1>
						<p>1 blog post with category ${(category)!''}.<br /></p>
					<#else>
						<p>${(totalPosts)!''} blog posts with category ${(category)!''}.<br /></p>
					</#if>

					<#list posts as post>
						<#include "/templates/blog/card-post.ftl">
					</#list>

					<#include "/templates/fragment/pagination.ftl">

				<#else>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No posts were found with that category.</p>
				</#if>

				<div class="text-center">
					<p><a href="/category/">See more categories...</a></p>
				</div>
			</div>

			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>
