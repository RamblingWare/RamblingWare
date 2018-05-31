<!DOCTYPE HTML>
<html>
<head>
<#include "../fragment/meta/meta.ftl">

<title>Year ${(context.year)!''} - ${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "../fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-time"></span>&nbsp;Year: ${(context.year)!''}</h1>

				<#if context.posts??>
					<p>${(context.totalPosts)!''} blog posts published in ${(context.year)!''}.<br /></p>

					<#list context.posts as post>
						<#include "../blog/card-post.ftl">
					</#list>

					<#include "../fragment/pagination.ftl">

				<#else>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No results were found for that year.</p>
				</#if>

				<div class="text-center">
					<p><a href="/year/">See more years...</a></p>
				</div>
			</div>

			<#include "../fragment/archive.ftl">
		</div>
	</article>

	<#include "../fragment/footer.ftl">
</body>
</html>
