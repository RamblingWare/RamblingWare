<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Tags - ${(name)!'Oddox'}</title>
</head>
<body>

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-tag"></span>&nbsp;Tags</h1>

				<#if tags??>
					<#if tags.size() == 1>
						<p>1 tag found.<br /></p>
					<#else>
						<p>${(tags.size())!''} tags found. (Sorted alphabetically).<br /></p>
					</#if>
					<ol>
					<#list tags as tag>
						<li><a title="${(tag.name)!'?'}" href="/tag/${(tag.name)!''}" class="text-bold">${(tag.name)!'?'}</a>
						&nbsp;(${(tag.count)!'?'} posts)
						</li>
					</#list>
					</ol>
				<#else>
					<p class="padding border round border-danger text-danger text-bold">
					<span class="icon-cross text-medium margin-right"></span>
						No tags were found.</p>
				</#if>
			</div>

			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>
