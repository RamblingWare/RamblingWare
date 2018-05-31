<!DOCTYPE HTML>
<html>
<head>
<#include "../fragment/meta/meta.ftl">

<title>Tags - ${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "../fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-tag"></span>&nbsp;Tags</h1>

				<#if context.tags??>
					<p>${(context.tags?size)!''} tags found. (Sorted alphabetically).<br /></p>
					<ol>
					<#list context.tags as tag>
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

			<#include "../fragment/archive.ftl">
		</div>
	</article>

	<#include "../fragment/footer.ftl">
</body>
</html>
