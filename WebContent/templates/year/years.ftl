<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Years - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-time w3-text-theme"></span>&nbsp;Years</h1>
				
				<#if years??>
					<#if years.size() == 1>	
						<p>1 year of blog posts.<br /></p>
					<#else>
						<p>${(years.size())!''} years of blog posts.<br /></p>
					</#if>
					<ul>
					<#list years as year>
						<li><a title="${(year.name)!''}" href="/year/${(year.name)!''}">${(year.name)!''}</a>
						&nbsp;(${(year.count)!''} posts)
						</li>
					</#list>
					</ul>				
				<#else>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No results were found for any year.</p>
				</#if>
			</div>
			
			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>