<!DOCTYPE HTML>
<html>
<head>
<#include "/WEB-INF/templates/fragment/meta/meta.ftl">

<title>Tags - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/WEB-INF/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/WEB-INF/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-time w3-text-theme"></span>&nbsp;Tags</h1>
				
				<#if tags??>
					<#if tags.size() == 1>	
						<p>1 tag found.<br /></p>
					<#else>
						<p>${(tags.size())!''} tags found. (Sorted alphabetically).<br /></p>
					</#if>
					<ol>
					<#list tags as tag>
						<li><a class="tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" title="${(tag.name)!''}" href="/tag/${(tag.name)!''}">${(tag.name)!''}</a>
						&nbsp;(${(tag.count)!''} posts)
						</li>
					</#list>
					</ol>				
				<#else>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No tags were found.</p>
				</#if>
			</div>
			
			<#include "/WEB-INF/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/WEB-INF/templates/fragment/footer.ftl">
</body>
</html>