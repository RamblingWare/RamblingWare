<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Categories - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-folder w3-text-theme"></span>&nbsp;Categories</h1>
				
				<#if categories??>
					<#if categories.size() == 1>	
						<p>1 category found.<br /></p>
					<#else>
						<p>${(categories.size())!''} categories found. (Sorted alphabetically).<br /></p>
					</#if>
					<ol>
					<#list categories as category>
						<li><a title="${(category.name)!''}" href="/category/${(category.name)!''}">${(category.name)!''}</a>
						&nbsp;(${(category.count)!''} posts)
						</li>
					</#list>
					</ol>				
				<#else>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No categories were found.</p>
				</#if>
			</div>
			
			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>