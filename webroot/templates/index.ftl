<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>${(name)!'Oddox'}</title>
</head>
<body>

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">
			
				<h1>Blog Posts</h1>
				
				<#if posts??>
					<#list posts as post>
						<#include "/templates/blog/card-post.ftl">
					</#list>
	
					<#include "/templates/fragment/pagination.ftl">
				<#else>
					<p class="padding border round border-danger text-danger text-bold">
					<span class="icon-cross text-medium margin-right"></span>
						Something went wrong because no posts were found. Please try again later?
					</p>
				</#if>
			</div>
	
			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>
