<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Categories - ${(name)!'Oddox'}</title>
</head>
<body>

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-folder"></span>&nbsp;Categories</h1>

				<#if categories??>
					<#if categories.size() == 1>
						<p>1 category found.<br /></p>
					<#else>
						<p>${(categories.size())!''} categories found. (Sorted alphabetically).<br /></p>
					</#if>
					<ol>
					<#list categories as category>
						<li><a title="${(category.name)!'?'}" href="/category/${(category.name)!''}" class="text-bold">${(category.name)!'?'}</a>
						&nbsp;(${(category.count)!'?'} posts)
						</li>
					</#list>
					</ol>
				<#else>
					<p class="padding border round border-danger text-danger text-bold">
					<span class="icon-cross text-medium margin-right"></span>
						No categories were found.</p>
				</#if>
			</div>

			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>
