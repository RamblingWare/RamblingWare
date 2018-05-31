<!DOCTYPE HTML>
<html>
<head>
<#include "../fragment/meta/meta.ftl">

<title>Categories - ${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "../fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-folder"></span>&nbsp;Categories</h1>

				<#if context.categories??>
					<p>${(context.categories?size)!''} categories found. (Sorted alphabetically).<br /></p>
					<ol>
					<#list context.categories as category>
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

			<#include "../fragment/archive.ftl">
		</div>
	</article>

	<#include "../fragment/footer.ftl">
</body>
</html>
