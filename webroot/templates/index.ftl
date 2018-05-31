<!DOCTYPE HTML>
<html>
<head>
<#include "fragment/meta/meta.ftl">

<title>${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">
			
				<h1>Blog Posts</h1>
				
				<#if context.posts??>
					<#list context.posts as post>
						<#include "blog/card-post.ftl">
					</#list>
	
					<#include "fragment/pagination.ftl">
				<#else>
					<p class="padding border round border-danger text-danger text-bold">
					<span class="icon-cross text-medium margin-right"></span>
						Something went wrong because no posts were found. Please try again later?
					</p>
				</#if>
			</div>
	
			<#include "fragment/archive.ftl">
		</div>
	</article>

	<#include "fragment/footer.ftl">
</body>
</html>
