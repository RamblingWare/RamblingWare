<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Year ${(year)!''} - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-time w3-text-theme"></span>&nbsp;Year: ${(year)!''}</h1>
				
				<#if posts??>
					<#if posts.size() == 1>	
						<p>1 blog post published in ${(year)!''}.<br /></p>
					<#else>
						<p>${(totalPosts)!''} blog posts published in ${(year)!''}.<br /></p>
					</#if>
					
					<#list posts as post>
						<#include "/templates/blog/card-post.ftl">
					</#list>
					
					<#include "/templates/fragment/pagination.ftl">
				
				<#else>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No results were found for that year.</p>
				</#if>
				
				<div class="w3-container w3-padding-left w3-padding-right w3-center">
					<hr />
					<p class="w3-large"><a href="/year/">See more years...</a></p>
				</div>
			</div>
			
			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>