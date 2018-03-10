<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Years - ${(name)!'Oddox'}</title>
</head>
<body>

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-time"></span>&nbsp;Years</h1>

				<#if years??>
					<#if years.size() == 1>
						<p>1 year of blog posts.<br /></p>
					<#else>
						<p>${(years.size())!''} years of blog posts.<br /></p>
					</#if>
					<ul>
					<#list years as year>
						<li><a title="${(year.name)!'?'}" href="/year/${(year.name)!''}" class="text-bold">${(year.name)!'?'}</a>
						&nbsp;(${(year.count)!'?'} posts)
						</li>
					</#list>
					</ul>
				<#else>
					<p class="padding border round border-danger text-danger text-bold">
					<span class="icon-cross text-medium margin-right"></span>
						No results were found for any year.</p>
				</#if>
			</div>

			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>
